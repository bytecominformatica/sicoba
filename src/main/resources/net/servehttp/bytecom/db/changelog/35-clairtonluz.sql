ALTER TABLE mensalidade
ADD COLUMN modalidade int;

UPDATE mensalidade SET modalidade = 24;

ALTER TABLE mensalidade
MODIFY modalidade int NOT NULL;
