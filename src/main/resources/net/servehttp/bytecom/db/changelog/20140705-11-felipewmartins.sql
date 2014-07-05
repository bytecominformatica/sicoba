--liquibase formatted sql

--changeset felipewmartins:11
ALTER TABLE usuario ADD COLUMN img BLOB AFTER senha;