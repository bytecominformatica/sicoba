package br.com.clairtonluz.bytecom.model.entity.comercial;

import br.com.clairtonluz.bytecom.model.entity.extra.EntityGeneric;

import javax.persistence.*;

@Entity
@Table(name = "estado")
public class Estado extends EntityGeneric {

    private static final long serialVersionUID = -6261865881321268540L;
    private String nome;
    private String uf;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pais_id")
    private Pais pais;

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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

}
