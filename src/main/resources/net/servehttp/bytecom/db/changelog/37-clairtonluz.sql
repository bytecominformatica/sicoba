ALTER TABLE mensalidade
DROP INDEX uk_mensalidade_numero_boleto,
ADD CONSTRAINT uk_mensalidade_modalidade_numero_boleto
UNIQUE(modalidade, numero_boleto);
