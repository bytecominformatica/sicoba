create table plano (
    id int not null primary key auto_increment,
    nome varchar(100) not null,
    upload int not null default 0,
    download int not null default 0,
    modalidade varchar(150),
    valor_instalacao dec(20,2),
    valor_mensalidade dec(20,2),
    created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp
);

create trigger plano_on_insert before insert
on plano for each row set new.created_at = current_timestamp;

create table cliente (
    id int not null primary key auto_increment,
    nome varchar(100) not null,
    rg varchar(20),
    cpf varchar(20),
    email varchar(140),
    endereco varchar(200),
    fone_titular varchar(20) not null,
    contato varchar(100),
    fone_contato varchar(20),
    created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    unique key(rg),
    unique key(cpf),
    unique key(email)
);

create trigger cliente_on_insert before insert
on cliente for each row set new.created_at = current_timestamp;

create table equipamento (
    id int not null primary key auto_increment,
    marca varchar(30) not null, 
    modelo varchar(30) not null, 
    mac varchar(20) not null, 
    tipo tinyint not null,
    created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    unique key(mac)
);

create trigger equipamento_on_insert before insert
on equipamento for each row set new.created_at = current_timestamp;

create table contrato (
  id int(11) not null auto_increment,
  cliente_id int(11) not null,
  plano_id int(11) not null,
  vencimento tinyint(4) not null,
  datainstalacao date not null,
  equipamento_id int(11) default null,
  equipamento_wifi_id int(11) default null,
  created_at datetime default null,
  updated_at timestamp not null default current_timestamp on update current_timestamp,
  primary key (id),
  unique key equipamento_id (equipamento_id),
  unique key equipamento_wifi_id (equipamento_wifi_id),
  foreign key (cliente_id) references cliente (id),
  foreign key (plano_id) references plano (id),
  foreign key (equipamento_id) references equipamento (id),
  foreign key (equipamento_wifi_id) references equipamento (id)
);

create trigger contrato_on_insert before insert
on contrato for each row set new.created_at = current_timestamp;

create table acesso (
    id int not null primary key auto_increment,
    ip varchar(20) not null, 
    mascara varchar(20) not null, 
    gateway varchar(20) not null, 
    mac varchar(20) not null, 
    status tinyint not null default 0,
    cliente_id int not null,
    created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    unique key(ip),
    unique key(mac),
    foreign key(cliente_id) references cliente(id)
);

create trigger acesso_on_insert before insert
on acesso for each row set new.created_at = current_timestamp;

create table mensalidade (
    id int not null primary key auto_increment,
    data_vencimento date not null,
    valor dec(20,2) not null default 0,
    cliente_id int not null,
    status tinyint not null default 0,
    created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    unique key(cliente_id,data_vencimento),
    foreign key(cliente_id) references cliente(id)
);

create trigger mensalidade_on_insert before insert
on mensalidade for each row set new.created_at = current_timestamp;

create table usuario (
    id int not null primary key AUTO_INCREMENT,
    nome varchar(100) not null,
    login varchar(20) not null,
    senha varchar(100) not null,
    unique key(login),
    created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp
);

create trigger usuario_on_insert before insert
on usuario for each row set new.created_at = current_timestamp;

--20140107

alter table acesso
add constraint uk_acesso_cliente_id unique key(cliente_id);

alter table contrato
add constraint uk_contrato_cliente_id unique key(cliente_id);

--20140129

alter table equipamento
add column descricao varchar(255) after id,
add column status int not null default 0 after tipo;


create table pais(
	id int not null primary key auto_increment,
	nome varchar(255) not null,
	unique key(nome)
);

insert into pais (nome) values ('Brasil');

create table estado(
	id int not null primary key auto_increment,
	nome varchar(255) not null,
	uf varchar(2) not null,
	pais_id int,
	constraint fk_estado_pais_id
	foreign key(pais_id) references pais(id),
	unique key(nome, pais_id)
);

insert into estado (nome, uf, pais_id) values ('Ceará','CE', 1);

create table cidade(
	id int not null primary key auto_increment,
	nome varchar(255) not null,
	estado_id int,
	constraint fk_cidade_estado_id
	foreign key(estado_id) references estado(id),
	unique key(nome, estado_id)
);

insert into cidade (nome, estado_id) values ('Caucaia', 1);
insert into cidade (nome, estado_id) values ('Fortaleza', 1);

create table bairro(
	id int not null primary key auto_increment,
	nome varchar(255) not null,
	cidade_id int,
	constraint fk_bairro_cidade_id
	foreign key(cidade_id) references cidade(id),
	unique key(nome, cidade_id)
	
);

insert into bairro (nome, cidade_id) values ('Conj. Metropolitano', 1);
insert into bairro (nome, cidade_id) values ('Patrícia Gomes', 1);
insert into bairro (nome, cidade_id) values ('Nova Metrópole', 1);
insert into bairro (nome, cidade_id) values ('Arianópoles', 1);
insert into bairro (nome, cidade_id) values ('Metrópole 5', 1);
insert into bairro (nome, cidade_id) values ('Urucutuba', 1);

insert into bairro (nome, cidade_id) values ('Antônio Bezerra', 2);

create table endereco(
	id int not null primary key auto_increment,
	logradouro varchar(255) not null,
	numero varchar(255),
	complemento varchar(255),
	bairro_id int,
	constraint fk_endereco_bairro_id
	foreign key(bairro_id) references bairro(id)
);


alter table cliente
add column endereco_id int,
add constraint fk_cliente_endereco_id
foreign key(endereco_id) references endereco(id);

--PRODUÇÃO

alter table contrato 
change column dataInstalacao data_instalacao date not null;

alter table plano 
add column wifi bit not null default 0 after modalidade;

update plano set wifi=1 where modalidade = 'Com Wifi';

alter table plano 
drop column modalidade;

alter table cliente
add column cnpj varchar(255) after cpf,
add constraint uk_cliente_cnpj
unique key(cnpj);


CREATE TABLE empresa(
	id int not null primary key auto_increment,
  	cnpj varchar(255) NOT NULL,
  	nome varchar(255) NOT NULL,
  	razao_social varchar(255) NOT NULL,
  	fone varchar(255),
  	endereco_id int,
  	created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
  	FOREIGN KEY(endereco_id) REFERENCES endereco (id)
);

create trigger empresa_on_insert before insert
on empresa for each row set new.created_at = current_timestamp;


CREATE TABLE fornecedor(
	id int not null primary key auto_increment,
  	nome varchar(255) NOT NULL,
  	fone varchar(255),
  	empresa_id int,
  	created_at datetime,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
  	FOREIGN KEY(empresa_id) REFERENCES endereco (id)
);

create trigger fornecedor_on_insert before insert
on fornecedor for each row set new.created_at = current_timestamp;

alter table endereco
add column cep varchar(255) not null;

alter table cliente
change cpf cpf_cnpj varchar(255);

alter table cliente
drop column cnpj;


INSERT INTO estado(id,uf,nome,pais_id)VALUES(99,'TT','CearaMigration',1);
update cidade set estado_id = 99 where estado_id = 1;
delete from estado where id = 1;

INSERT INTO estado(id,uf,nome,pais_id)VALUES(1,'AC','Acre',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(2,'AL','Alagoas',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(3,'AM','Amazonas',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(4,'AP','Amapá',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(5,'BA','Bahia',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(6,'CE','Ceará',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(7,'DF','Distrito Federal',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(8,'ES','Espírito Santo',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(9,'GO','Goiás',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(10,'MA','Maranhão',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(11,'MG','Minas Gerais',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(12,'MS','Mato Grosso do Sul',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(13,'MT','Mato Grosso',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(14,'PA','Pará',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(15,'PB','Paraíba',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(16,'PE','Pernambuco',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(17,'PI','Piauí',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(18,'PR','Paraná',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(19,'RJ','Rio de Janeiro',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(20,'RN','Rio Grande do Norte',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(21,'RR','Roraima',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(22,'RO','Rondônia',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(23,'RS','Rio Grande do Sul',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(24,'SC','Santa Catarina',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(25,'SE','Sergipe',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(26,'SP','São Paulo',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(27,'TO','Tocantins',1);

update cidade set estado_id = 6 where estado_id = 99;
delete from estado where id = 99;