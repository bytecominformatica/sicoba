--liquibase formatted sql

--changeset felipewmartins:3
ALTER TABLE fornecedor DROP foreign key fornecedor_ibfk_1;
ALTER TABLE fornecedor CHANGE empresa_id endereco_id INT(11);
ALTER TABLE fornecedor ADD CONSTRAINT fk_endereco_fornecedor FOREIGN KEY (endereco_id) REFERENCES endereco(id);

