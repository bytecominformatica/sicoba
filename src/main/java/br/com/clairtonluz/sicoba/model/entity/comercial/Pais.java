package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.EntityGeneric;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pais")
public class Pais extends EntityGeneric {

    private String nome;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
