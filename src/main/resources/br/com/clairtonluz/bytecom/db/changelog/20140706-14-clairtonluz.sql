--liquibase formatted sql

--changeset clairtonluz:12

alter table mensalidade
add column valor_pago dec(20,2) not null default 0 after valor;

alter table mensalidade
add column tarifa dec(20,2) not null default 0 after valor_pago;

alter table mensalidade
add column data_ocorrencia date after data_vencimento;