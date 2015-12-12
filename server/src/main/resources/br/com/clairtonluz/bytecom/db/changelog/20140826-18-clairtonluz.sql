--liquibase formatted sql

--changeset clairtonluz:18
alter table plano drop column wifi;

alter table cliente add column status tinyint not null default 0;

update cliente set status = 1 where id in (SELECT cliente_id from acesso where status = 1);
update cliente set status = 2 where id in (SELECT cliente_id from acesso where status = 2);

alter table acesso drop column status;