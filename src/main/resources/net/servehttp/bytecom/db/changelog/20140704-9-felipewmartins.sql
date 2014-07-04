--liquibase formatted sql

--changeset felipewmartins:9
ALTER TABLE usuario ADD COLUMN email VARCHAR(255) AFTER login;