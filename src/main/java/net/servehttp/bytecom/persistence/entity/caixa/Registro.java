package net.servehttp.bytecom.persistence.entity.caixa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "registro")
public class Registro implements Serializable {

	private static final long serialVersionUID = -6545044532807044580L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "modalidade_nosso_numero")
	private int modalidadeNossoNumero;
	@Column(name = "nosso_numero")
	private int nossoNumero;
	private Date vencimento;
	@Column(name = "valor_titulo")
	private double valorTitulo;
	@Column(name = "valor_tarifa")
	private double valorTarifa;

	@ManyToOne
	@JoinColumn(name = "header_lote_id")
	private HeaderLote headerLote;
	
	@OneToOne(mappedBy="registro", cascade= CascadeType.ALL)
	private RegistroDetalhe registroDetalhe;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

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

	public RegistroDetalhe getRegistroDetalhe() {
		return registroDetalhe;
	}

	public void setRegistroDetalhe(RegistroDetalhe registroDetalhe) {
		this.registroDetalhe = registroDetalhe;
	}

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
