package br.com.clairtonluz.bytecom.model.jpa.entity.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cidade")
public class Cidade extends EntityGeneric {
    private static final long serialVersionUID = -4732281989184639857L;

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
