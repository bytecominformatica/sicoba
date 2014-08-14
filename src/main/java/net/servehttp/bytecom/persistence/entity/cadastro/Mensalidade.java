package net.servehttp.bytecom.persistence.entity.cadastro;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.servehttp.bytecom.util.DateUtil;
import net.servehttp.bytecom.util.StringUtil;

/**
 *
 * @author clairton
 */
@Entity
@Table(name = "mensalidade")
public class Mensalidade implements Serializable {

  private static final long serialVersionUID = -8955481650524371350L;
  public static final int NAO_PAGA = 0;
  public static final int PAGA = 1;
  public static final int BAIXA = 2;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "data_vencimento")
  @Temporal(TemporalType.DATE)
  private Date dataVencimento;
  @Column(name = "data_ocorrencia")
  @Temporal(TemporalType.DATE)
  private Date dataOcorrencia;
  private double valor;
  @Column(name = "valor_pago")
  private double valorPago;
  private double desconto;
  private double tarifa;
  private int status;
  @Column(name = "numero_boleto")
  private Integer numeroBoleto;

  @JoinColumn(name = "cliente_id")
  @ManyToOne(fetch = FetchType.EAGER)
  private Cliente cliente;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  public Mensalidade() {}

  public Mensalidade(Integer id) {
    this.setId(id);
  }

  public Date getDataVencimento() {
    return dataVencimento;
  }

  public String getDataVencimentoFormatada() {
    return DateUtil.INSTANCE.format(dataVencimento);
  }

  public void setDataVencimento(Date dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getStatusFormatado() {
    String s = "STATUS INVÁLIDO";
    switch (status) {
      case PAGA:
        s = "PAGA";
        break;
      case NAO_PAGA:
        s = "EM ABERTO";
        break;
      case BAIXA:
        s = "BAIXA";
        break;
    }
    return s;
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

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Integer getNumeroBoleto() {
    return numeroBoleto;
  }

  public void setNumeroBoleto(Integer numeroBoleto) {
    this.numeroBoleto = numeroBoleto;
  }

  public double getValorPago() {
    return valorPago;
  }

  public void setValorPago(double valorPago) {
    this.valorPago = valorPago;
  }

  public double getTarifa() {
    return tarifa;
  }

  public void setTarifa(double tarifa) {
    this.tarifa = tarifa;
  }

  public Date getDataOcorrencia() {
    return dataOcorrencia;
  }

  public void setDataOcorrencia(Date dataOcorrencia) {
    this.dataOcorrencia = dataOcorrencia;
  }

  public String getTarifaFormatada() {
    return StringUtil.INSTANCE.formatCurrence(tarifa);
  }

  public String getValorFormatada() {
    return StringUtil.INSTANCE.formatCurrence(valor);
  }

  public String getValorPagoFormatada() {
    return StringUtil.INSTANCE.formatCurrence(valorPago);
  }

  public double getDesconto() {
    return desconto;
  }

  public void setDesconto(double desconto) {
    this.desconto = desconto;
  }


}
