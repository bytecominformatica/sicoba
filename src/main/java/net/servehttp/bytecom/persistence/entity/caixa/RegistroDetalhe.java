package net.servehttp.bytecom.persistence.entity.caixa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "registro_detalhe")
public class RegistroDetalhe implements Serializable {

	private static final long serialVersionUID = 3727868029617370955L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "juros_multas_encargos")
	private double jurosMultasEncargos;
	private double desconto;
	private double abatimento;
	private double iof;
	@Column(name = "valor_pago")
	private double valorPago;
	@Column(name = "valor_liquido")
	private double valorLiquido;
	@Column(name = "data_ocorrencia")
	private Date dataOcorrencia;
	@Column(name = "data_credito")
	private Date dataCredito;
	@Column(name = "data_debito_tarifa")
	private Date dataDebitoTarifa;
	
	@OneToOne
	@JoinColumn(name = "registro_id")
	private Registro registro;

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

	public double getJurosMultasEncargos() {
		return jurosMultasEncargos;
	}

	public void setJurosMultasEncargos(double jurosMultasEncargos) {
		this.jurosMultasEncargos = jurosMultasEncargos;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double getAbatimento() {
		return abatimento;
	}

	public void setAbatimento(double abatimento) {
		this.abatimento = abatimento;
	}

	public double getIof() {
		return iof;
	}

	public void setIof(double iof) {
		this.iof = iof;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Date getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(Date dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public Date getDataCredito() {
		return dataCredito;
	}

	public void setDataCredito(Date dataCredito) {
		this.dataCredito = dataCredito;
	}

	public Date getDataDebitoTarifa() {
		return dataDebitoTarifa;
	}

	public void setDataDebitoTarifa(Date dataDebitoTarifa) {
		this.dataDebitoTarifa = dataDebitoTarifa;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
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
