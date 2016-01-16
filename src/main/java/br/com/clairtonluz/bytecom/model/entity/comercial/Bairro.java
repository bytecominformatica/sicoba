package br.com.clairtonluz.bytecom.model.entity.comercial;

import br.com.clairtonluz.bytecom.model.entity.extra.EntityGeneric;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "bairro")
public class Bairro extends EntityGeneric {

    private static final long serialVersionUID = 4219357783343963670L;

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
