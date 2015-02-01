alter table cedente
add column created_at datetime,
add column updated_at timestamp not null default current_timestamp on update current_timestamp;

update cedente set created_at = now();

alter table cedente
change created_at created_at datetime not null;
