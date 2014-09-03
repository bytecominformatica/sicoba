create table ponto_transmissao(
  id int not null primary key auto_increment,
  ip1 int not null,
  ip2 int not null,
  ip3 int not null,
  ip4 int not null,
  mac varchar(255),
  recebe_de int,
  observacoes text,
  created_at datetime,
  updated_at timestamp not null default current_timestamp on update current_timestamp,
  constraint fk_ponto_transmissao_recebe_de_to_ponto_transmissao
  foreign key(recebe_de) references ponto_transmissao(id)
);