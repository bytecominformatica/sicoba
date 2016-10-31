package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "trailer_lote")
public class TrailerLote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "trailer_lote_id_seq")
    @SequenceGenerator(name = "trailer_lote_id_seq", sequenceName = "trailer_lote_id_seq")
    private Integer id;
    @Column(name = "quantidade_registro_lote")
    int quantidadeRegistroLote;

    @OneToOne
    @JoinColumn(name = "header_lote_id")
    private HeaderLote headerLote;

    public int getQuantidadeRegistroLote() {
        return quantidadeRegistroLote;
    }

    public void setQuantidadeRegistroLote(int quantidadeRegistroLote) {
        this.quantidadeRegistroLote = quantidadeRegistroLote;
    }

    public HeaderLote getHeaderLote() {
        return headerLote;
    }

    public void setHeaderLote(HeaderLote headerLote) {
        this.headerLote = headerLote;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
