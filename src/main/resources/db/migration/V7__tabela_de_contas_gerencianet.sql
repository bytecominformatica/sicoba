CREATE TABLE gerencianet_account (
  id             SERIAL       NOT NULL PRIMARY KEY,
  nome           VARCHAR(255) NOT NULL,
  client_id      VARCHAR(255) NOT NULL,
  client_secret  VARCHAR(255) NOT NULL,
  sandbox        BOOLEAN      NOT NULL DEFAULT FALSE,
  notify_cliente BOOLEAN      NOT NULL DEFAULT FALSE,
  fine           DECIMAL(10, 2),
  interest       DECIMAL(10, 2),
  created_at     TIMESTAMP    NOT NULL,
  updated_at     TIMESTAMP    NOT NULL,
  UNIQUE (client_id)
);

ALTER TABLE charge
  ADD COLUMN gerencianet_account_id INTEGER,
  ADD CONSTRAINT fk_charge_gerencianet_account_id
FOREIGN KEY (gerencianet_account_id)
REFERENCES gerencianet_account (id);
ALTER TABLE carnet
  ADD COLUMN gerencianet_account_id INTEGER,
  ADD CONSTRAINT fk_carnet_gerencianet_account_id
FOREIGN KEY (gerencianet_account_id)
REFERENCES gerencianet_account (id);

INSERT INTO gerencianet_account
(nome, client_id, client_secret, sandbox, notify_cliente, fine, interest, created_at, updated_at)
VALUES ('default', 'teste', 'teste', TRUE, FALSE, 5.0, 3.3, now(), now());

UPDATE charge
SET gerencianet_account_id = (SELECT id
                              FROM gerencianet_account
                              WHERE client_id = 'teste');
UPDATE carnet
SET gerencianet_account_id = (SELECT id
                              FROM gerencianet_account
                              WHERE client_id = 'teste');
ALTER TABLE charge
  ALTER COLUMN gerencianet_account_id SET NOT NULL;
ALTER TABLE carnet
  ALTER COLUMN gerencianet_account_id SET NOT NULL;