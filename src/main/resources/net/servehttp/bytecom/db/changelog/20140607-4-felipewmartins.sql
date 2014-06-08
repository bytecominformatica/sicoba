--liquibase formatted sql

--changeset felipewmartins:4

INSERT INTO usuario (nome, login, senha) VALUES('Administrador','admin','21232f297a57a5a743894a0e4a801fc3')