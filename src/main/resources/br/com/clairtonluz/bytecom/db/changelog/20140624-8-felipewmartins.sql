--liquibase formatted sql

--changeset felipewmartins:8
CREATE TABLE tecnico (
	id int not null primary key auto_increment,
	nome varchar(255) not null,
	fone varchar(20) not null,
	email varchar(140),
	created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE=InnoDB;

create trigger tecnico_on_insert before insert
on tecnico for each row set new.created_at = current_timestamp;