--liquibase formatted sql

--changeset clairtonluz:16

create table access_group (
	id int not null primary key auto_increment,
	name varchar(255) not null,
	description varchar(255),
	user_default tinyint(1) not null default 0,
	unique key(name)
) ENGINE=InnoDB;

create table user_account (
	id int not null primary key auto_increment,
	email varchar(255) not null,
	first_name varchar(255),
	last_name varchar(255),
	img blob,
	unique key(email),
	created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE=InnoDB;

create trigger user_account_on_insert before insert
on user_account for each row set new.created_at = current_timestamp;

create table user_group (
	group_id int not null,
	user_id int not null,
	primary key(group_id, user_id),
	constraint fk_group_id_access_group_id
	foreign key(group_id) references access_group(id),
	constraint fk_user_id_user_accounnt_id
	foreign key(user_id) references user_account(id)
) ENGINE=InnoDB;

create table authentication (
	id int not null primary key auto_increment,
	username varchar(255) not null,
	password varchar(255) not null,
	user_account_id int not null,
	unique key(username),
	unique key(user_account_id),
	constraint fk_user_account_id_user_accounnt_id
	foreign key(user_account_id) references user_account(id),
	created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE=InnoDB;

create trigger authentication_on_insert before insert
on authentication for each row set new.created_at = current_timestamp;

insert into user_account (email, first_name, last_name, img, updated_at) SELECT email, substring_index(nome, ' ', 1), substring_index(nome, ' ', -2), img, created_at FROM usuario where email is not null;
insert into authentication (username, password, user_account_id, updated_at) SELECT u.login, u.senha, ua.id, u.created_at FROM usuario u inner join user_account ua on substring_index(nome, ' ', 1) = ua.first_name where u.email is not null;
insert into access_group (name, description) values ('ADMIN', 'Administrador do sistema'), ('DIRETORIA', 'Diretores da empresa'), ('FINANCEIRO', 'Acesso a funções financeiras do sistema');
insert user_group (user_id, group_id) select ua.id, (select ag.id from access_group ag where name = 'ADMIN') from user_account ua;

update user_account set created_at = updated_at;
update authentication set created_at = updated_at;

drop table usuario;