create table cedente (
	id int not null primary key auto_increment,
	codigo int not null default 0,
	digito_verificador int not null default 0,
	nome varchar(255) not null,
	cpf_cnpj varchar(255) not null,
	numero_agencia int not null,
	digito_agencia int not null default 0,
	codigo_operacao int not null default 0,
	numero_conta int not null,
	digito_conta int not null
);