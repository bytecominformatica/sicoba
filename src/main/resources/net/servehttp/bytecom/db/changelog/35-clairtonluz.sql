ALTER TABLE mensalidade
ADD COLUMN modalidade INT;

UPDATE mensalidade
SET modalidade = 24;

ALTER TABLE mensalidade
MODIFY modalidade INT NOT NULL;
