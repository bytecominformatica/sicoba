package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.EntityGeneric;

import javax.persistence.*;

@Entity
@Table(name = "pais")
public class Pais extends EntityGeneric {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais_id_seq")
    @SequenceGenerator(name = "pais_id_seq", sequenceName = "pais_id_seq")
    private Integer id;
    private String nome;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
