package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.EntityGeneric;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cidade")
public class Cidade extends EntityGeneric {

    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String toString() {
        return nome;
    }

}
