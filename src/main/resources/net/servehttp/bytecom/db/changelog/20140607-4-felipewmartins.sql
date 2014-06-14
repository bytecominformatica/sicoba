--liquibase formatted sql

--changeset felipewmartins:4

INSERT INTO usuario (nome, login, senha) VALUES('Administrador','admin',sha2('xdedi2321dl', 256))