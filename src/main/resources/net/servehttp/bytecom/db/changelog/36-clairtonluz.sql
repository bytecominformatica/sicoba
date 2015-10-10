ALTER TABLE mensalidade
ADD COLUMN baixa_manual BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE mensalidade SET baixa_manual = TRUE WHERE status = 2;
UPDATE mensalidade set baixa_manual = TRUE WHERE numero_boleto is null and status = 1 and valor_pago = 0;

ALTER TABLE mensalidade
DROP COLUMN tarifa,
DROP COLUMN parcela,
DROP COLUMN status;
