create table conexao (
	id int not null primary key auto_increment,
	cliente_id int not null,
	mikrotik_id int not null,
	nome varchar(255) not null,
	senha varchar(255) not null,
	created_at datetime,
  	updated_at timestamp not null default current_timestamp on update current_timestamp,
  	constraint fk_conexao_cliente_id foreign key(cliente_id) references cliente(id),
  	constraint fk_conexao_mikrotik_id foreign key(mikrotik_id) references mikrotik(id),
  	constraint uk_conexao_nome unique key(nome)
);