--liquibase formatted sql

--changeset clairtonluz:15

alter table mensalidade
add column desconto dec(20,2) not null default 0 after valor_pago;