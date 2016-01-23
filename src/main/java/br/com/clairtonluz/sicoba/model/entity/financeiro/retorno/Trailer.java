package br.com.clairtonluz.sicoba.model.entity.financeiro.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.EntityGeneric;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trailer")
public class Trailer extends EntityGeneric {

    private int quantidadeLotes;
    private int quantidadeRegistros;

    @OneToOne
    @JoinColumn(name = "header_id")
    private Header header;

    public int getQuantidadeLotes() {
        return quantidadeLotes;
    }

    public void setQuantidadeLotes(int quantidadeLotes) {
        this.quantidadeLotes = quantidadeLotes;
    }

    public int getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    public void setQuantidadeRegistros(int quantidadeRegistros) {
        this.quantidadeRegistros = quantidadeRegistros;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

}
