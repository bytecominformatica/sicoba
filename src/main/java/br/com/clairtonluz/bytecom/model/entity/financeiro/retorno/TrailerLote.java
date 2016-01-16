package br.com.clairtonluz.bytecom.model.entity.financeiro.retorno;

import br.com.clairtonluz.bytecom.model.entity.extra.EntityGeneric;

import javax.persistence.*;

@Entity
@Table(name = "trailer_lote")
public class TrailerLote extends EntityGeneric {

    private static final long serialVersionUID = 5463523145491779905L;

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

}
