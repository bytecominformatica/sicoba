--liquibase formatted sql

--changeset clairtonluz:12

alter table mensalidade
add column numero_boleto int;