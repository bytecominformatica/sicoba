package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "pais")
public class Pais extends BaseEntity {

    private String nome;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
