--liquibase formatted sql

--changeset clairtonluz:16

create table access_group (
	id int not null primary key auto_increment,
	name varchar(255) not null,
	description varchar(255),
	user_default tinyint(1),
	unique key(name)
);

create table user_account (
	id int not null primary key auto_increment,
	email varchar(255) not null,
	firt_name varchar(255),
	last_name varchar(255),
	unique key(email)
);

create table user_group (
	id int not null primary key auto_increment,
	group_id int not null,
	user_id int not null,
	username varchar(255) not null,
	group_name varchar(255) not null,
	constraint fk_group_id_access_group_id
	foreign key(group_id) references access_group(id),
	constraint fk_user_id_user_accounnt_id
	foreign key(user_id) references user_account(id),
	constraint uk_user_group_group_id_user_id
	unique key(group_id, user_id)
);

create table authentication (
	id int not null primary key auto_increment,
	username varchar(255) not null,
	password varchar(255) not null,
	user_account_id int not null,
	unique key(username),
	constraint fk_user_account_id_user_accounnt_id
	foreign key(user_account_id) references user_account(id)
);