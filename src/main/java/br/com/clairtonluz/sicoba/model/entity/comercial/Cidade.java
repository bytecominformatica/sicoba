package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.EntityGeneric;

import javax.persistence.*;

@Entity
@Table(name = "cidade")
public class Cidade extends EntityGeneric {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cidade_id_seq")
    @SequenceGenerator(name = "cidade_id_seq", sequenceName = "cidade_id_seq")
    private Integer id;
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

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
