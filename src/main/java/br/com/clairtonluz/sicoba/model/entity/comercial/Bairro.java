package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.EntityGeneric;

import javax.persistence.*;


@Entity
@Table(name = "bairro")
public class Bairro extends EntityGeneric {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bairro_id_seq")
    @SequenceGenerator(name = "bairro_id_seq", sequenceName = "bairro_id_seq")
    private Integer id;
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

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
