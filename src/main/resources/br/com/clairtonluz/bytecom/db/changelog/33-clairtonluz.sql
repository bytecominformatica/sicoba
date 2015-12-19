alter table mikrotik
change nome name varchar(255) not null,
change descricao description text,
change usuario login varchar(255) not null,
change senha pass varchar(255) not null default '',
change porta port int not null default 8728;