CREATE TABLE pais
(
  id         SERIAL                 NOT NULL PRIMARY KEY,
  nome       CHARACTER VARYING(255) NOT NULL,
  created_at TIMESTAMP              NOT NULL,
  updated_at TIMESTAMP              NOT NULL,
  UNIQUE (nome)
);

CREATE TABLE estado (
  id         SERIAL                 NOT NULL PRIMARY KEY,
  nome       CHARACTER VARYING(255) NOT NULL,
  uf         CHARACTER VARYING(2)   NOT NULL,
  pais_id    INT                    NOT NULL REFERENCES pais (id),
  created_at TIMESTAMP              NOT NULL,
  updated_at TIMESTAMP              NOT NULL,
  UNIQUE (nome, pais_id)
);

CREATE TABLE cidade (
  id         SERIAL                 NOT NULL PRIMARY KEY,
  nome       CHARACTER VARYING(255) NOT NULL,
  estado_id  INT                    NOT NULL REFERENCES estado (id),
  created_at TIMESTAMP              NOT NULL,
  updated_at TIMESTAMP              NOT NULL,
  UNIQUE (nome, estado_id)
);

CREATE TABLE bairro (
  id         SERIAL                 NOT NULL PRIMARY KEY,
  nome       CHARACTER VARYING(255) NOT NULL,
  cidade_id  INT                    NOT NULL REFERENCES cidade (id),
  created_at TIMESTAMP              NOT NULL,
  updated_at TIMESTAMP              NOT NULL,
  UNIQUE (nome, cidade_id)
);

CREATE TABLE cedente (
  id                 SERIAL                 NOT NULL PRIMARY KEY,
  codigo             INT                    NOT NULL DEFAULT 0,
  digito_verificador INT                    NOT NULL DEFAULT 0,
  nome               CHARACTER VARYING(255) NOT NULL,
  cpf_cnpj           CHARACTER VARYING(255) NOT NULL,
  numero_agencia     INT                    NOT NULL,
  digito_agencia     INT                    NOT NULL DEFAULT 0,
  codigo_operacao    INT                    NOT NULL DEFAULT 0,
  numero_conta       INT                    NOT NULL,
  digito_conta       INT                    NOT NULL,
  created_at         TIMESTAMP              NOT NULL,
  updated_at         TIMESTAMP              NOT NULL,
  prazo              INT                    NOT NULL DEFAULT '60',
  multa              DECIMAL(12, 2)         NOT NULL DEFAULT '5.00',
  juros              DECIMAL(12, 2)         NOT NULL DEFAULT '0.67'
);

CREATE TABLE endereco (
  id          SERIAL                 NOT NULL PRIMARY KEY,
  logradouro  CHARACTER VARYING(255) NOT NULL,
  numero      CHARACTER VARYING(255) DEFAULT NULL,
  complemento CHARACTER VARYING(255) DEFAULT NULL,
  bairro_id   INT                    NOT NULL REFERENCES bairro (id),
  cep         CHARACTER VARYING(255) NOT NULL,
  created_at  TIMESTAMP              NOT NULL,
  updated_at  TIMESTAMP              NOT NULL
);

CREATE TABLE cliente (
  id            SERIAL                 NOT NULL PRIMARY KEY,
  nome          CHARACTER VARYING(100) NOT NULL,
  rg            CHARACTER VARYING(20)           DEFAULT NULL,
  cpf_cnpj      CHARACTER VARYING(255)          DEFAULT NULL,
  dt_nascimento DATE                            DEFAULT NULL,
  email         CHARACTER VARYING(140)          DEFAULT NULL,
  fone_titular  CHARACTER VARYING(20)  NOT NULL,
  contato       CHARACTER VARYING(100)          DEFAULT NULL,
  fone_contato  CHARACTER VARYING(20)           DEFAULT NULL,
  created_at    TIMESTAMP              NOT NULL,
  updated_at    TIMESTAMP              NOT NULL,
  endereco_id   INT                             DEFAULT NULL REFERENCES endereco (id),
  status        SMALLINT               NOT NULL DEFAULT 0,
  UNIQUE (rg),
  UNIQUE (cpf_cnpj),
  UNIQUE (email)
);

CREATE TABLE mikrotik (
  id          SERIAL                 NOT NULL PRIMARY KEY,
  name        CHARACTER VARYING(255) NOT NULL,
  description TEXT,
  login       CHARACTER VARYING(255) NOT NULL,
  pass        CHARACTER VARYING(255) NOT NULL DEFAULT '',
  host        CHARACTER VARYING(255) NOT NULL,
  port        INT                    NOT NULL DEFAULT '8728',
  created_at  TIMESTAMP              NOT NULL,
  updated_at  TIMESTAMP              NOT NULL
);

CREATE TABLE conexao (
  id          SERIAL                 NOT NULL PRIMARY KEY,
  cliente_id  INT                    NOT NULL REFERENCES cliente (id),
  mikrotik_id INT                    NOT NULL REFERENCES mikrotik (id),
  nome        CHARACTER VARYING(255) NOT NULL,
  senha       CHARACTER VARYING(255) NOT NULL,
  ip          CHARACTER VARYING(40) DEFAULT NULL,
  created_at  TIMESTAMP              NOT NULL,
  updated_at  TIMESTAMP              NOT NULL,
  UNIQUE (nome),
  UNIQUE (ip)
);

CREATE TABLE plano (
  id                SERIAL                 NOT NULL PRIMARY KEY,
  nome              CHARACTER VARYING(100) NOT NULL,
  upload            INT                    NOT NULL DEFAULT 0,
  download          INT                    NOT NULL DEFAULT 0,
  valor_instalacao  DECIMAL(20, 2)                  DEFAULT NULL,
  valor DECIMAL(20, 2)                  DEFAULT NULL,
  created_at        TIMESTAMP              NOT NULL,
  updated_at        TIMESTAMP              NOT NULL
);

CREATE TABLE equipamento (
  id         SERIAL                NOT NULL PRIMARY KEY,
  descricao  CHARACTER VARYING(255)         DEFAULT NULL,
  marca      CHARACTER VARYING(30) NOT NULL,
  modelo     CHARACTER VARYING(30) NOT NULL,
  mac        CHARACTER VARYING(20) NOT NULL,
  tipo       SMALLINT              NOT NULL,
  status     SMALLINT              NOT NULL DEFAULT 0,
  created_at TIMESTAMP             NOT NULL,
  updated_at TIMESTAMP             NOT NULL,

  UNIQUE (mac)
);

CREATE TABLE contrato (
  id                  SERIAL    NOT NULL PRIMARY KEY,
  cliente_id          INT       NOT NULL REFERENCES cliente (id),
  plano_id            INT       NOT NULL REFERENCES plano (id),
  vencimento          SMALLINT  NOT NULL,
  data_instalacao     DATE      NOT NULL,
  equipamento_id      INT DEFAULT NULL REFERENCES equipamento (id),
  equipamento_wifi_id INT DEFAULT NULL REFERENCES equipamento (id),
  created_at          TIMESTAMP NOT NULL,
  updated_at          TIMESTAMP NOT NULL,

  UNIQUE (cliente_id),
  UNIQUE (equipamento_id),
  UNIQUE (equipamento_wifi_id)
);

CREATE TABLE header (
  id           SERIAL    NOT NULL PRIMARY KEY,
  sequencial   INT       NOT NULL,
  nome_arquivo CHARACTER VARYING(255) DEFAULT NULL,
  data_geracao TIMESTAMP              DEFAULT NULL,
  created_at   TIMESTAMP NOT NULL,
  updated_at   TIMESTAMP NOT NULL,

  UNIQUE (sequencial)
);

CREATE TABLE header_lote (
  id                            SERIAL    NOT NULL PRIMARY KEY,
  numero_remessa_retorno        INT  DEFAULT NULL,
  data_gravacao_remessa_retorno DATE DEFAULT NULL,
  header_id                     INT  DEFAULT NULL REFERENCES header (id),
  created_at                    TIMESTAMP NOT NULL,
  updated_at                    TIMESTAMP NOT NULL
);

CREATE TABLE titulo (
  id              SERIAL         NOT NULL PRIMARY KEY,
  data_vencimento DATE                    DEFAULT NULL,
  data_ocorrencia DATE                    DEFAULT NULL,
  valor           DECIMAL(20, 2)          DEFAULT NULL,
  valor_pago      DECIMAL(20, 2) NOT NULL DEFAULT 0.00,
  desconto        DECIMAL(20, 2) NOT NULL DEFAULT 0.00,
  tarifa          DECIMAL(20, 2) NOT NULL DEFAULT 0.00,
  cliente_id      INT            NOT NULL REFERENCES cliente (id),
  status          SMALLINT       NOT NULL DEFAULT 0,
  created_at      TIMESTAMP      NOT NULL,
  updated_at      TIMESTAMP      NOT NULL,
  numero_boleto   INT                     DEFAULT NULL,
  modalidade      INT            NOT NULL DEFAULT '24',

  UNIQUE (numero_boleto)
);

CREATE TABLE registro (
  id                           SERIAL    NOT NULL PRIMARY KEY,
  modalidade_nosso_numero      INT                    DEFAULT NULL,
  nosso_numero                 INT                    DEFAULT NULL,
  vencimento                   DATE                   DEFAULT NULL,
  valor_titulo                 DECIMAL(20, 2)         DEFAULT NULL,
  valor_tarifa                 DECIMAL(20, 2)         DEFAULT NULL,
  header_lote_id               INT                    DEFAULT NULL REFERENCES header_lote (id),
  numero_documento             CHARACTER VARYING(255) DEFAULT NULL,
  banco_recebedor              CHARACTER VARYING(255) DEFAULT NULL,
  agencia_recebedor            CHARACTER VARYING(255) DEFAULT NULL,
  digito_verificador_recebedor CHARACTER VARYING(255) DEFAULT NULL,
  codigo_movimento             INT                    DEFAULT NULL,
  uso_da_empresa               CHARACTER VARYING(255) DEFAULT NULL,
  created_at                   TIMESTAMP NOT NULL,
  updated_at                   TIMESTAMP NOT NULL
);

CREATE TABLE registro_detalhe (
  id                    SERIAL    NOT NULL PRIMARY KEY,
  juros_multas_encargos DECIMAL(20, 2) DEFAULT NULL,
  desconto              DECIMAL(20, 2) DEFAULT NULL,
  abatimento            DECIMAL(20, 2) DEFAULT NULL,
  iof                   DECIMAL(20, 2) DEFAULT NULL,
  valor_pago            DECIMAL(20, 2) DEFAULT NULL,
  valor_liquido         DECIMAL(20, 2) DEFAULT NULL,
  data_ocorrencia       DATE           DEFAULT NULL,
  data_credito          DATE           DEFAULT NULL,
  data_debito_tarifa    DATE           DEFAULT NULL,
  registro_id           INT            DEFAULT NULL REFERENCES registro (id),
  created_at            TIMESTAMP NOT NULL,
  updated_at            TIMESTAMP NOT NULL
);

CREATE TABLE trailer (
  id                  SERIAL    NOT NULL PRIMARY KEY,
  quantidade_lotes     INT       NOT NULL,
  quantidade_registros INT       NOT NULL,
  header_id           INT       NOT NULL REFERENCES header (id),
  created_at          TIMESTAMP NOT NULL,
  updated_at          TIMESTAMP NOT NULL
);

CREATE TABLE trailer_lote (
  id                       SERIAL    NOT NULL PRIMARY KEY,
  quantidade_registro_lote INT DEFAULT NULL,
  header_lote_id           INT DEFAULT NULL REFERENCES header_lote (id),
  created_at               TIMESTAMP NOT NULL,
  updated_at               TIMESTAMP NOT NULL
);

CREATE TABLE users (
  id       SERIAL       NOT NULL PRIMARY KEY,
  name     VARCHAR(150) NOT NULL,
  username VARCHAR(45)  NOT NULL,
  password VARCHAR(255) NOT NULL,
  enabled  boolean      NOT NULL DEFAULT true,
  created_at            TIMESTAMP NOT NULL,
  updated_at            TIMESTAMP NOT NULL,
  UNIQUE (username)
);

CREATE TABLE user_roles (
  id      SERIAL      NOT NULL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users (id),
  role    VARCHAR(45) NOT NULL,
  created_at          TIMESTAMP NOT NULL,
  updated_at          TIMESTAMP NOT NULL,
  UNIQUE (role, user_id)
);