package br.com.clairtonluz.sicoba.model.entity.extra;

import java.time.LocalDateTime;

public interface BaseEntityGets {

    public Integer getId();

    public LocalDateTime getCreatedAt();

    public LocalDateTime getUpdatedAt();

    public String getCreatedBy();

    public String getUpdatedBy();
}
