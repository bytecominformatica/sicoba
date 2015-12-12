INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (1,'1MB',128,1024,150.00,35.00,{ts '2013-10-24 18:58:15'},{ts '2014-08-30 13:38:33'});
INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (2,'2MB',256,2048,150.00,50.00,{ts '2013-10-24 18:58:35'},{ts '2014-08-30 13:38:33'});
INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (3,'3MB',384,3072,150.00,65.00,{ts '2013-10-31 18:44:49'},{ts '2014-08-30 13:38:33'});
INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (5,'4MB',400,4096,150.00,75.00,{ts '2013-12-21 11:34:47'},{ts '2014-08-30 13:38:33'});
INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (6,'CORPORATIVO',1000,5000,0.00,0.00,{ts '2014-01-08 17:17:16'},{ts '2014-03-19 22:09:02'});
INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (7,'5MB',512,5120,150.00,85.00,{ts '2014-01-23 16:58:30'},{ts '2014-08-30 13:38:33'});
INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (8,'2MG Wallace',200,2000,0.00,49.90,{ts '2015-10-28 09:53:54'},{ts '2015-10-28 09:53:54'});
INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (9,'4MG Wallace',0,4012,0.00,59.90,{ts '2015-10-28 09:54:11'},{ts '2015-10-28 09:54:11'});
INSERT INTO plano (id,nome,upload,download,valor_instalacao,valor_mensalidade,created_at,updated_at) VALUES (10,'5MG',0,5000,0.00,69.90,{ts '2015-10-28 09:54:54'},{ts '2015-10-28 09:54:54'});

INSERT INTO equipamento (id,descricao,marca,modelo,mac,tipo,status,created_at,updated_at) VALUES (7,null,'Ubiquiti','NanoStation M5','DC:9F:DB:2A:7F:F0',0,0,{ts '2013-11-22 22:19:38'},{ts '2013-11-22 22:19:38'});
INSERT INTO equipamento (id,descricao,marca,modelo,mac,tipo,status,created_at,updated_at) VALUES (9,null,'Ubiquiti','NanoStation M5','DC:9F:DB:E6:13:FB',0,0,{ts '2013-11-22 22:20:28'},{ts '2013-11-22 22:20:28'});
INSERT INTO equipamento (id,descricao,marca,modelo,mac,tipo,status,created_at,updated_at) VALUES (33,'','Intelbrass','WOM 5000','00:1A:3F:B4:0E:0C',0,0,{ts '2014-02-13 17:54:46'},{ts '2014-02-13 17:54:46'});
INSERT INTO equipamento (id,descricao,marca,modelo,mac,tipo,status,created_at,updated_at) VALUES (42,'instalado na leyde','intelbra','wom5000','00:1A:3F:CD:83:00',0,0,{ts '2014-03-13 16:14:13'},{ts '2014-03-13 16:14:13'});
INSERT INTO equipamento (id,descricao,marca,modelo,mac,tipo,status,created_at,updated_at) VALUES (85,'Instalado no cliente francisco','INTELBRAS','WOM 5000','00:1A:3F:D0:12:D8',0,0,{ts '2014-07-17 20:36:31'},{ts '2014-07-17 20:36:31'});
INSERT INTO equipamento (id,descricao,marca,modelo,mac,tipo,status,created_at,updated_at) VALUES (89,'instalado no cliente antonio cesar','INTELBRAS','WOM 5000','00:1A:3F:BD:F5:02',1,0,{ts '2014-07-28 18:00:22'},{ts '2014-07-28 18:00:22'});

INSERT INTO pais (id,nome,created_at,updated_at) VALUES (1,'Brasil',{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});

INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (1,'Acre','AC',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (2,'Alagoas','AL',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (3,'Amazonas','AM',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (4,'Amapá','AP',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (5,'Bahia','BA',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (6,'Ceará','CE',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (7,'Distrito Federal','DF',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (8,'Espírito Santo','ES',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (9,'Goiás','GO',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (10,'Maranhão','MA',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (11,'Minas Gerais','MG',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (12,'Mato Grosso do Sul','MS',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (13,'Mato Grosso','MT',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (14,'Pará','PA',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (15,'Paraíba','PB',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (16,'Pernambuco','PE',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (17,'Piauí','PI',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (18,'Paraná','PR',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (19,'Rio de Janeiro','RJ',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (20,'Rio Grande do Norte','RN',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (21,'Roraima','RR',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (22,'Rondônia','RO',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (23,'Rio Grande do Sul','RS',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (24,'Santa Catarina','SC',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (25,'Sergipe','SE',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (26,'São Paulo','SP',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO estado (id,nome,uf,pais_id,created_at,updated_at) VALUES (27,'Tocantins','TO',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});

INSERT INTO cidade (id,nome,estado_id,created_at,updated_at) VALUES (1,'Caucaia',6,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO cidade (id,nome,estado_id,created_at,updated_at) VALUES (2,'Fortaleza',6,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});

INSERT INTO bairro (id,nome,cidade_id,created_at,updated_at) VALUES (2,'Patrícia Gomes',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO bairro (id,nome,cidade_id,created_at,updated_at) VALUES (5,'Metrópole 5',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO bairro (id,nome,cidade_id,created_at,updated_at) VALUES (6,'Urucutuba',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO bairro (id,nome,cidade_id,created_at,updated_at) VALUES (7,'Antônio Bezerra',2,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO bairro (id,nome,cidade_id,created_at,updated_at) VALUES (8,'Parque Albano',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO bairro (id,nome,cidade_id,created_at,updated_at) VALUES (9,'Conjunto Metropolitano',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO bairro (id,nome,cidade_id,created_at,updated_at) VALUES (10,'Nova Metrópole (Jurema)',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO bairro (id,nome,cidade_id,created_at,updated_at) VALUES (11,'Arianópolis (Jurema)',1,{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});

INSERT INTO endereco (id,logradouro,numero,complemento,bairro_id,cep,created_at,updated_at) VALUES (4,'Rua 30','999','',2,'',{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO endereco (id,logradouro,numero,complemento,bairro_id,cep,created_at,updated_at) VALUES (6,'Rua 23 de Maio','123',null,2,'61607070',{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO endereco (id,logradouro,numero,complemento,bairro_id,cep,created_at,updated_at) VALUES (8,'Rua 30','1234',null,2,'61607045',{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO endereco (id,logradouro,numero,complemento,bairro_id,cep,created_at,updated_at) VALUES (9,'Rua 7 de Setembro','213','',2,'',{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO endereco (id,logradouro,numero,complemento,bairro_id,cep,created_at,updated_at) VALUES (10,'Rua 13 de abril','456',null,2,'61607110',{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});
INSERT INTO endereco (id,logradouro,numero,complemento,bairro_id,cep,created_at,updated_at) VALUES (25,'Rua 7 de Setembro','34','',2,'',{ts '2015-01-08 22:27:10'},{ts '2015-01-08 22:27:10'});

INSERT INTO cliente (id,nome,rg,cpf_cnpj,dt_nascimento,email,fone_titular,contato,fone_contato,created_at,updated_at,endereco_id,status) VALUES (7,'CAUÊ DIEGO ARTHUR DE PAULA','132131231','445.954.531-40',null,'pedro@ccsoft.com.br','(85)9269-6074','ESPOSA','(85)8824-5653',{ts '2013-11-22 22:24:47'},{ts '2014-08-30 12:55:43'},4,1);
INSERT INTO cliente (id,nome,rg,cpf_cnpj,dt_nascimento,email,fone_titular,contato,fone_contato,created_at,updated_at,endereco_id,status) VALUES (8,'NATHAN LUCAS DOS SANTOS',null,'728.836.477-12',null,'fulano@ccsoft.com.br','(85)8817-8446',null,null,{ts '2013-11-22 22:26:50'},{ts '2015-01-22 10:59:49'},6,1);
INSERT INTO cliente (id,nome,rg,cpf_cnpj,dt_nascimento,email,fone_titular,contato,fone_contato,created_at,updated_at,endereco_id,status) VALUES (9,'BRYAN CÉSAR DIAS','1231223','802.437.861-23',null,'gustavo_p_martins@otimatransportes.com','(85)8888-3119','FERNANDO','(85)8816-1130',{ts '2013-11-22 22:31:54'},{ts '2014-08-30 12:55:43'},8,1);
INSERT INTO cliente (id,nome,rg,cpf_cnpj,dt_nascimento,email,fone_titular,contato,fone_contato,created_at,updated_at,endereco_id,status) VALUES (11,'BRYAN GABRIEL GOMES','3453453434','359.387.154-82',null,'ian.felipe.cavalcanti@l3ambiental.com.br','(85)42212381','ZECA','8586988221',{ts '2013-11-22 22:34:15'},{ts '2014-08-30 12:55:43'},9,2);
INSERT INTO cliente (id,nome,rg,cpf_cnpj,dt_nascimento,email,fone_titular,contato,fone_contato,created_at,updated_at,endereco_id,status) VALUES (12,'GIOVANNI IGOR RENAN DE PAULA','77998','314.189.451-53',null,'nlribeiro@stricker.eu.com','(85)8611-5150',null,'(85)8698-8271',{ts '2013-11-22 22:35:56'},{ts '2014-08-30 12:55:43'},10,1);
INSERT INTO cliente (id,nome,rg,cpf_cnpj,dt_nascimento,email,fone_titular,contato,fone_contato,created_at,updated_at,endereco_id,status) VALUES (14,'GUSTAVO PEDRO HENRIQUE MARTINS','23423423425','068.209.411-02',null,'henrique_t_fernandes@ovi.com','8582243489','FULANO','8533859279',{ts '2013-11-22 22:37:38'},{ts '2014-08-30 12:55:43'},25,1);

INSERT INTO contrato (id,cliente_id,plano_id,vencimento,data_instalacao,equipamento_id,equipamento_wifi_id,created_at,updated_at) VALUES (1,7,2,5,now(),7,null,{ts '2013-11-22 22:53:49'},{ts '2014-02-12 19:41:27'});
INSERT INTO contrato (id,cliente_id,plano_id,vencimento,data_instalacao,equipamento_id,equipamento_wifi_id,created_at,updated_at) VALUES (2,8,1,5,{d '2013-03-03'},85,89,{ts '2013-11-22 22:55:17'},{ts '2014-08-30 13:38:33'});
INSERT INTO contrato (id,cliente_id,plano_id,vencimento,data_instalacao,equipamento_id,equipamento_wifi_id,created_at,updated_at) VALUES (3,9,1,5,{d '2013-03-15'},9,null,{ts '2013-11-22 22:56:18'},{ts '2013-11-22 22:56:18'});
INSERT INTO contrato (id,cliente_id,plano_id,vencimento,data_instalacao,equipamento_id,equipamento_wifi_id,created_at,updated_at) VALUES (4,11,1,5,{d '2012-09-08'},null,null,{ts '2013-11-22 22:57:13'},{ts '2013-11-22 22:57:13'});
INSERT INTO contrato (id,cliente_id,plano_id,vencimento,data_instalacao,equipamento_id,equipamento_wifi_id,created_at,updated_at) VALUES (5,12,2,5,{d '2012-11-04'},42,null,{ts '2013-11-22 22:58:27'},{ts '2013-11-22 22:58:27'});
INSERT INTO contrato (id,cliente_id,plano_id,vencimento,data_instalacao,equipamento_id,equipamento_wifi_id,created_at,updated_at) VALUES (6,14,7,5,{d '2012-09-08'},33,null,{ts '2013-11-22 22:59:16'},{ts '2014-08-30 13:38:33'});

