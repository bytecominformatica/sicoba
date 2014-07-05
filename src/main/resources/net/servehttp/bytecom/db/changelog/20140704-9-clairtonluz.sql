--liquibase formatted sql

--changeset clairtonluz:9


CREATE TABLE header (
	id int not null primary key auto_increment,
	sequencial int not null,
	nome_arquivo varchar(255),
	data_geracao datetime,
	created_at datetime,
    	updated_at timestamp not null default current_timestamp on update current_timestamp,
    	constraint uk_sequencial 
    	unique key(sequencial)
) ENGINE=InnoDB;


create trigger header_on_insert before insert
on header for each row set new.created_at = current_timestamp;

CREATE TABLE header_lote (
	id int not null primary key auto_increment,
	numero_remessa_retorno int,
	data_gravacao_remessa_retorno date,
	header_id int,
	created_at datetime,
    	updated_at timestamp not null default current_timestamp on update current_timestamp,
    	constraint fk_header_lote_header_id
    	foreign key(header_id) references header(id)
) ENGINE=InnoDB;


create trigger header_lote_on_insert before insert
on header_lote for each row set new.created_at = current_timestamp;

CREATE TABLE trailer (
	id int not null primary key auto_increment,
	quantidadeLotes int not null,
	quantidadeRegistros int not null,
	header_id int not null,
	created_at datetime,
    	updated_at timestamp not null default current_timestamp on update current_timestamp,
    	constraint fk_trailer_header_id
    	foreign key(header_id) references header(id)
) ENGINE=InnoDB;

create trigger trailer_on_insert before insert
on trailer for each row set new.created_at = current_timestamp;

CREATE TABLE registro (
	id int not null primary key auto_increment,
	modalidade_nosso_numero int,
	nosso_numero int,
	vencimento date,
	valor_titulo dec(20,2),
	valor_tarifa dec(20,2),
	header_lote_id int,
	created_at datetime,
    	updated_at timestamp not null default current_timestamp on update current_timestamp,
    	constraint fk_registro_header_lote_id
    	foreign key(header_lote_id) references header_lote(id),
    	constraint uk_modalidade_nosso_numero_e_nosso_numero 
    	unique key(modalidade_nosso_numero, nosso_numero)
) ENGINE=InnoDB;

create trigger registro_on_insert before insert
on registro for each row set new.created_at = current_timestamp;

CREATE TABLE registro_detalhe (
	id int not null primary key auto_increment,
	juros_multas_encargos dec(20,2),
	desconto dec(20,2),
	abatimento dec(20,2),
	iof dec(20,2),
	valor_pago dec(20,2),
	valor_liquido dec(20,2),
	data_ocorrencia date,
	data_credito date,
	data_debito_tarifa date,
	registro_id int,
	created_at datetime,
    	updated_at timestamp not null default current_timestamp on update current_timestamp,
    	constraint fk_regitro_id
    	foreign key(registro_id) references registro(id)
) ENGINE=InnoDB;


create trigger registro_detalhe_on_insert before insert
on registro_detalhe for each row set new.created_at = current_timestamp;


CREATE TABLE trailer_lote (
	id int not null primary key auto_increment,
	quantidade_registro_lote int,
	header_lote_id int,
	created_at datetime,
    	updated_at timestamp not null default current_timestamp on update current_timestamp,
    	constraint fk_trailer_lote_header_lote_id
    	foreign key(header_lote_id) references header_lote(id)
) ENGINE=InnoDB;


create trigger trailer_lote_on_insert before insert
on trailer_lote for each row set new.created_at = current_timestamp;