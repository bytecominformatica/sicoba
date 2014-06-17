--liquibase formatted sql

--changeset felipewmartins:7
ALTER TABLE fornecedor ADD COLUMN cpf_cnpj VARCHAR(255) AFTER endereco_id;