package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "bairro")
public class Bairro extends BaseEntity {

    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cidade cidade;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
