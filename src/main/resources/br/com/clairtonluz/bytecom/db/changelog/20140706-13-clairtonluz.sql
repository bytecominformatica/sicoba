--liquibase formatted sql

--changeset clairtonluz:12

alter table mensalidade
add constraint uk_mensalidade_numero_boleto
unique key(numero_boleto);