ALTER TABLE bairro
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE cedente
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE cidade
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE cliente
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE conexao
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE contrato
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE endereco
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE equipamento
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE estado
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE header
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE header_lote
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE mikrotik
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE pais
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE plano
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE registro
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE registro_detalhe
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE titulo
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE trailer
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE trailer_lote
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE users
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
ALTER TABLE user_roles
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);

UPDATE bairro
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE cedente
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE cidade
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE cliente
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE conexao
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE contrato
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE endereco
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE equipamento
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE estado
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE header
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE header_lote
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE mikrotik
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE pais
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE plano
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE registro
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE registro_detalhe
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE titulo
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE trailer
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE trailer_lote
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE users
SET created_by = 'indefinido', updated_by = 'indefinido';
UPDATE user_roles
SET created_by = 'indefinido', updated_by = 'indefinido';

ALTER TABLE bairro
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE cedente
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE cidade
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE cliente
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE conexao
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE contrato
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE endereco
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE equipamento
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE estado
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE header
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE header_lote
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE mikrotik
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE pais
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE plano
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE registro
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE registro_detalhe
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE titulo
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE trailer
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE trailer_lote
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE users
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE user_roles
ALTER COLUMN created_by SET NOT NULL,
ALTER COLUMN updated_by SET NOT NULL;