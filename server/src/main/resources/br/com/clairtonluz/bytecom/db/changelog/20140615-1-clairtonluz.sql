--liquibase formatted sql

--changeset clairtonluz:1
alter table mensalidade
drop foreign key mensalidade_ibfk_1;

alter table mensalidade
drop index cliente_id;

alter table mensalidade
add constraint fk_mensalidade_cliente_id
foreign key(cliente_id) references cliente(id);