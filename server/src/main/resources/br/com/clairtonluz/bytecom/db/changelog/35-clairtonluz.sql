alter table mensalidade
    drop INDEX uk_mensalidade_numero_boleto,
    add column modalidade int not null default 24,
    ADD CONSTRAINT uk_mensalidade_modalidade_numero_boleto
    UNIQUE(modalidade, numero_boleto);
