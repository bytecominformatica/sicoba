package br.com.clairtonluz.bytecom.model.jpa.entity.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pais")
public class Pais extends EntityGeneric {

    private static final long serialVersionUID = -8042147881454631916L;

    private String nome;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
