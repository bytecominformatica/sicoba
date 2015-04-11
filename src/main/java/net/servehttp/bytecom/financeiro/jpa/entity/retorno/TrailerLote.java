package net.servehttp.bytecom.financeiro.jpa.entity.retorno;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;

@Entity
@Table(name = "trailer_lote")
public class TrailerLote extends EntityGeneric implements Serializable {

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
