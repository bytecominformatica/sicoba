alter table bairro
add column created_at datetime,
add column updated_at timestamp not null default current_timestamp on update current_timestamp;

alter table cidade
add column created_at datetime,
add column updated_at timestamp not null default current_timestamp on update current_timestamp;

alter table estado
add column created_at datetime,
add column updated_at timestamp not null default current_timestamp on update current_timestamp;

alter table pais
add column created_at datetime,
add column updated_at timestamp not null default current_timestamp on update current_timestamp;

alter table endereco
add column created_at datetime,
add column updated_at timestamp not null default current_timestamp on update current_timestamp;
    
create trigger bairro_on_insert before insert
on bairro for each row set new.created_at = current_timestamp;

create trigger cidade_on_insert before insert
on cidade for each row set new.created_at = current_timestamp;

create trigger estado_on_insert before insert
on estado for each row set new.created_at = current_timestamp;

create trigger pais_on_insert before insert
on pais for each row set new.created_at = current_timestamp;

create trigger endereco_on_insert before insert
on endereco for each row set new.created_at = current_timestamp;

update bairro set created_at = now();
update cidade set created_at = now();
update estado set created_at = now();
update pais set created_at = now();
update endereco set created_at = now();
