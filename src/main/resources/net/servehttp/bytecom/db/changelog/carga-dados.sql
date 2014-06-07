--liquibase formatted sql

--changeset felipewmartins:2
INSERT INTO estado(id,uf,nome,pais_id)VALUES(99,'TT','CearaMigration',1);
update cidade set estado_id = 99 where estado_id = 1;
delete from estado where id = 1;

INSERT INTO estado(id,uf,nome,pais_id)VALUES(1,'AC','Acre',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(2,'AL','Alagoas',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(3,'AM','Amazonas',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(4,'AP','Amapá',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(5,'BA','Bahia',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(6,'CE','Ceará',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(7,'DF','Distrito Federal',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(8,'ES','Espírito Santo',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(9,'GO','Goiás',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(10,'MA','Maranhão',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(11,'MG','Minas Gerais',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(12,'MS','Mato Grosso do Sul',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(13,'MT','Mato Grosso',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(14,'PA','Pará',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(15,'PB','Paraíba',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(16,'PE','Pernambuco',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(17,'PI','Piauí',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(18,'PR','Paraná',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(19,'RJ','Rio de Janeiro',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(20,'RN','Rio Grande do Norte',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(21,'RR','Roraima',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(22,'RO','Rondônia',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(23,'RS','Rio Grande do Sul',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(24,'SC','Santa Catarina',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(25,'SE','Sergipe',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(26,'SP','São Paulo',1);
INSERT INTO estado(id,uf,nome,pais_id)VALUES(27,'TO','Tocantins',1);

update cidade set estado_id = 6 where estado_id = 99;
delete from estado where id = 99;