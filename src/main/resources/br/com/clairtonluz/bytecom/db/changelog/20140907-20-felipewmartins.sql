--liquibase formatted sql

--changeset felipewmartins:20
CREATE TABLE IF NOT EXISTS cliente_georeferencia(
	id int primary key not null auto_increment,
	cliente_id int not null,
	lat decimal(15,10) not null,
	lng decimal(15,10) not null,
	created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    FOREIGN KEY(cliente_id) REFERENCES cliente (id)
);

create trigger cliente_georeferencia_on_insert before insert
on cliente_georeferencia for each row set new.created_at = current_timestamp;