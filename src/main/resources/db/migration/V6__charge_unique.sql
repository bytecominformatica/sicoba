ALTER TABLE titulo
    ADD CONSTRAINT uk_titulo_charge_id
    UNIQUE (charge_id),
    ADD CONSTRAINT uk_titulo_charge_url
    UNIQUE (charge_url);
