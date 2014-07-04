package net.servehttp.bytecom.persistence.entity.caixa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "registro")
public class Registro implements Serializable {

	private static final long serialVersionUID = -6545044532807044580L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "numero_registro_no_lote")
	private int numeroRegistroNoLote;
	@Column(name = "modalidade_nosso_numero")
	private int modalidadeNossoNumero;
	@Column(name = "nosso_numero")
	private int nossoNumero;
	@Column(name = "codigo_carteira")
	private int codigoCarteira;
	private Date vencimento;
	@Column(name = "valor_titulo")
	private double valorTitulo;
	@Column(name = "valor_tarifa")
	private double valorTarifa;

	@ManyToOne
	@JoinColumn(name = "header_lote_id")
	private HeaderLote headerLote;
	
	@OneToOne(mappedBy="registro")
	private RegistroDetalhe registroDetalhe;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HeaderLote getHeaderLote() {
		return headerLote;
	}

	public void setHeaderLote(HeaderLote headerLote) {
		this.headerLote = headerLote;
	}

	public int getNumeroRegistroNoLote() {
		return numeroRegistroNoLote;
	}

	public void setNumeroRegistroNoLote(int numeroRegistroNoLote) {
		this.numeroRegistroNoLote = numeroRegistroNoLote;
	}

	public int getModalidadeNossoNumero() {
		return modalidadeNossoNumero;
	}

	public void setModalidadeNossoNumero(int modalidadeNossoNumero) {
		this.modalidadeNossoNumero = modalidadeNossoNumero;
	}

	public int getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(int nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public int getCodigoCarteira() {
		return codigoCarteira;
	}

	public void setCodigoCarteira(int codigoCarteira) {
		this.codigoCarteira = codigoCarteira;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public double getValorTitulo() {
		return valorTitulo;
	}

	public void setValorTitulo(double valorTitulo) {
		this.valorTitulo = valorTitulo;
	}

	public double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

}
