--liquibase formatted sql

--changeset felipewmartins:17
ALTER TABLE cliente ADD COLUMN dt_nascimento DATE AFTER cpf_cnpj;