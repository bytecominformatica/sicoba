criar as tabelas de nfe
CREATE TABLE nfe
(
  id         SERIAL                 NOT NULL PRIMARY KEY,
  cliente_id varchar(255) not null,
  nome varchar(255) not null,
  logradouro varchar(255) not null,
  numero varchar(255) not null,
  complemento varchar(255) not null,
  bairo varchar(255) not null,
  cidade varchar(255) not null,
  uf varchar(255) not null,
  cep varchar(255) not null,
  cnpj varchar(255) not null,
  ie varchar(255) not null,
  cpf varchar(255) not null,
  rg varchar(255) not null,
  diaDeVencimento varchar(255) not null,
  modelo varchar(255) not null,
  cfop varchar(255) not null,
  telefone varchar(255) not null,
  email varchar(255) not null,
  codigoConsumidor varchar(255) not null,
  tipoAssinante varchar(255) not null,
  tipoUtilizacao varchar(255) not null,
  dataEmissao varchar(255) not null,
  dataPrestacao varchar(255) not null,
  observacao varchar(255) not null,
  codigoMunicipio varchar(255) not null,
  itens varchar(255) not null,
  created_at     TIMESTAMP    NOT NULL,
  updated_at     TIMESTAMP    NOT NULL,
  created_by     VARCHAR(255) NOT NULL,
  updated_by     VARCHAR(255) NOT NULL,
);
