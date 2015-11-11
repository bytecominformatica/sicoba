alter table access_group
add column created_at datetime,
add column updated_at timestamp not null default current_timestamp on update current_timestamp;

drop table if exists despesa;
drop table if exists empresa;
drop table if exists fornecedor;
drop table if exists ponto_transmissao;