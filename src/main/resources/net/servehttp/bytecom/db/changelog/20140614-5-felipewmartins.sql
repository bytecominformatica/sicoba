--liquibase formatted sql
--changeset felipewmartins:5
ALTER TABLE despesa CHANGE status status char(2) not null;