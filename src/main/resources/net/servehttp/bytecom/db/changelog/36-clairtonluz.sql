CREATE TABLE pagamento(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  data DATE,
  valor double,
  desconto double,
  mensalidade_id INT,
  registro_id INT,
  created_at datetime,
  updated_at timestamp not null default current_timestamp on update current_timestamp,
  CONSTRAINT fk_pagamento_mensalidade_id
  FOREIGN KEY(mensalidade_id) REFERENCES mensalidade(id),
  CONSTRAINT fk_pagamento_registro_id
  FOREIGN KEY(registro_id) REFERENCES registro(id)
);

INSERT INTO pagamento (data, valor, desconto, mensalidade_id) select data_ocorrencia, valor_pago, desconto, id from mensalidade WHERE status = 2;
INSERT INTO pagamento (data, valor, desconto, mensalidade_id) select data_ocorrencia, valor_pago, desconto, id from mensalidade WHERE numero_boleto is null and status = 1 and valor_pago = 0;
INSERT INTO pagamento (mensalidade_id, registro_id) select m.id, r.id from registro r inner JOIN mensalidade m on r.nosso_numero = m.numero_boleto;
INSERT INTO pagamento (mensalidade_id, registro_id) select m.id, r.id from registro r inner JOIN mensalidade m on r.nosso_numero = m.id WHERE m.numero_boleto is null and m.tarifa > 0;

ALTER TABLE mensalidade
DROP COLUMN tarifa,
DROP COLUMN parcela,
DROP COLUMN data_ocorrencia,
DROP COLUMN valor_pago,
DROP COLUMN status;