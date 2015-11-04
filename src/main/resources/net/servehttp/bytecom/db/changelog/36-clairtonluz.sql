ALTER TABLE registro
ADD COLUMN numero_documento VARCHAR(255),
ADD COLUMN banco_recebedor VARCHAR(255),
ADD COLUMN agencia_recebedor VARCHAR(255),
ADD COLUMN digito_verificador_recebedor VARCHAR(255),
ADD COLUMN codigo_movimento int,
ADD COLUMN uso_da_empresa VARCHAR(255);

UPDATE registro SET codigo_movimento = 6;

