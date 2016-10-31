package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "trailer")
public class Trailer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "trailer_id_seq")
    @SequenceGenerator(name = "trailer_id_seq", sequenceName = "trailer_id_seq")
    private Integer id;
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

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
