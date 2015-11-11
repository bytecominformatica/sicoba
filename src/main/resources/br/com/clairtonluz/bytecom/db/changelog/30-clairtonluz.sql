delete from cliente_georeferencia;
alter table cliente_georeferencia 
add constraint uk_cliente_georeferencia_cliente_id unique key(cliente_id);