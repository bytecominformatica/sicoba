alter table conexao
add ip varchar(40),
add constraint uk_conexao_ip unique key(ip);