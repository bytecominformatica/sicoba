ALTER TABLE conexao
  ADD COLUMN mac VARCHAR(17);

UPDATE conexao
SET mac = e.mac
FROM (SELECT
        ct.cliente_id,
        e.mac
      FROM conexao cx
        INNER JOIN contrato ct ON ct.cliente_id = cx.cliente_id
        INNER JOIN equipamento e ON e.id = ct.equipamento_wifi_id
     ) AS e
WHERE conexao.cliente_id = e.cliente_id;


UPDATE conexao
SET mac = e.mac
FROM (SELECT
        ct.cliente_id,
        e.mac
      FROM conexao cx
        INNER JOIN contrato ct ON ct.cliente_id = cx.cliente_id
        INNER JOIN equipamento e ON e.id = ct.equipamento_id
     ) AS e
WHERE conexao.cliente_id = e.cliente_id;