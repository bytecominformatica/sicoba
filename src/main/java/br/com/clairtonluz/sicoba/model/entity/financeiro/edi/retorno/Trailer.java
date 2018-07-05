package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "trailer")
public class Trailer extends BaseEntity {

    @Column(name = "quantidade_lotes")
    private int quantidadeLotes;
    @Column(name = "quantidade_registros")
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
