-- removendo mascaras
UPDATE endereco
SET cep = replace(cep, '-', '');

UPDATE cliente
SET cpf_cnpj = replace(replace(cpf_cnpj, '.', ''), '-', '');

UPDATE cliente
SET
  fone_titular = replace(replace(replace(fone_titular, '(', ''), ')', ''), '-', ''),
  fone_contato = replace(replace(replace(fone_contato, '(', ''), ')', ''), '-', '');

-- criando tabelas


CREATE TABLE carnet (
  id                 SERIAL         NOT NULL PRIMARY KEY,
  carnet_id          INT,
  message            VARCHAR(80),
  description        VARCHAR(255)   NOT NULL,
  link               VARCHAR(255),
  cover              VARCHAR(255),
  status             VARCHAR(30),
  value              DECIMAL(20, 2) NOT NULL,
  discount           DECIMAL(20, 2),
  first_pay          DATE           NOT NULL,
  repeats            INT            NOT NULL,
  split_items        BOOLEAN        NOT NULL DEFAULT FALSE,
  token_notification VARCHAR(255),
  last_notification  INT,
  cliente_id         INT            NOT NULL CONSTRAINT fk_carnet_cliente_id_cliente_id REFERENCES cliente (id),
  created_by         VARCHAR(255)   NOT NULL,
  updated_by         VARCHAR(255)   NOT NULL,
  created_at         TIMESTAMP      NOT NULL,
  updated_at         TIMESTAMP      NOT NULL,
  CONSTRAINT uk_carnet_carnet_id UNIQUE (carnet_id),
  CONSTRAINT uk_carnet_link UNIQUE (link)
);

CREATE TABLE charge (
  id                 SERIAL         NOT NULL PRIMARY KEY,
  charge_id          INT,
  message            VARCHAR(80),
  description        VARCHAR(255)   NOT NULL,
  status             VARCHAR(30),
  url                VARCHAR(255),
  payment_url        VARCHAR(255),
  payment            VARCHAR(30),
  parcel             INT,
  token_notification VARCHAR(255),
  last_notification  INT,
  barcode            VARCHAR(255),
  value              DECIMAL(20, 2) NOT NULL,
  paid_value         DECIMAL(20, 2),
  discount           DECIMAL(20, 2),
  expire_at          DATE           NOT NULL,
  paid_at            DATE,
  carnet_id          INT CONSTRAINT fk_charge_carnet_id_carnet_id REFERENCES carnet (id),
  cliente_id         INT            NOT NULL CONSTRAINT fk_carnet_cliente_id_cliente_id REFERENCES cliente (id),
  created_by         VARCHAR(255)   NOT NULL,
  updated_by         VARCHAR(255)   NOT NULL,
  created_at         TIMESTAMP      NOT NULL,
  updated_at         TIMESTAMP      NOT NULL,
  CONSTRAINT uk_charge_charge_id UNIQUE (charge_id),
  CONSTRAINT uk_charge_carnet_id_and_parcel UNIQUE (carnet_id, parcel),
  CONSTRAINT uk_charge_url UNIQUE (url)
);