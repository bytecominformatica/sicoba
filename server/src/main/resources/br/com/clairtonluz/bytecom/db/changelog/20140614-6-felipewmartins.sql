--liquibase formatted sql

--changeset felipewmartins:6
ALTER TABLE fornecedor ADD COLUMN razao_social VARCHAR(255) NOT NULL AFTER nome;