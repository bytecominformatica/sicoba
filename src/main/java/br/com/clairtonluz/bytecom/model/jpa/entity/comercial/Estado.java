package br.com.clairtonluz.bytecom.model.jpa.entity.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "estado")
public class Estado extends EntityGeneric {

    private static final long serialVersionUID = -6261865881321268540L;

    private String nome;

    private String uf;

    @OneToMany(mappedBy = "estado", fetch = FetchType.EAGER)
    private List<Cidade> cidades;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pais_id")
    private Pais pais;

    public Estado() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<Cidade> getCidades() {
        return this.cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public Cidade addCidade(Cidade cidade) {
        getCidades().add(cidade);
        cidade.setEstado(this);

        return cidade;
    }

    public Cidade removeCidade(Cidade cidade) {
        getCidades().remove(cidade);
        cidade.setEstado(null);

        return cidade;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

}
