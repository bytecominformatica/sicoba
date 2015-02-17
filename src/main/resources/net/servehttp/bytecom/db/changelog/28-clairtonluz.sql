create table mikrotik (
	id int not null primary key auto_increment,
	nome varchar(255) not null,
	descricao text,
	usuario varchar(255) not null,
	senha varchar(255) not null default '',
	host varchar(255) not null,
	porta int not null default 8728,
	created_at datetime,
  	updated_at timestamp not null default current_timestamp on update current_timestamp
);