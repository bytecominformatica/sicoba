package net.servehttp.bytecom.persistence.entity.cadastro;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Mensalidade extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -8955481650524371350L;
  public static final int EM_ABERTO = 0;
  public static final int BOLETO_PAGO = 1;
  public static final int BAIXA_MANUAL = 2;
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
      case BOLETO_PAGO:
        s = "BOLETO PAGO";
        break;
      case EM_ABERTO:
        s = "EM ABERTO";
        break;
      case BAIXA_MANUAL:
        s = "BAIXA MANUAL";
        break;
      default:
        s = "STATUS INVÁLIDO";
        break;
    }
    return s;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
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
