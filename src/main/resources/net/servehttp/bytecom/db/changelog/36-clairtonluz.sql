ALTER TABLE mensalidade
ADD COLUMN baixa_manual BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE mensalidade SET baixa_manual = TRUE WHERE status = 2;

ALTER TABLE mensalidade
DROP COLUMN tarifa,
DROP COLUMN parcela,
DROP COLUMN status;
