alter table cedente
add column prazo int not null default 60,
add column multa dec(12,2) not null default 5,
add column juros dec(12,2) not null default 0.6677;