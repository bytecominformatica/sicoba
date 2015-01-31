package net.servehttp.bytecom.persistence.entity.caixa;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.servehttp.bytecom.converter.date.LocalDatePersistenceConverter;
import net.servehttp.bytecom.persistence.entity.cadastro.EntityGeneric;

@Entity
@Table(name = "registro")
public class Registro extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -6545044532807044580L;

  @Column(name = "modalidade_nosso_numero")
  private int modalidadeNossoNumero;
  @Column(name = "nosso_numero")
  private int nossoNumero;
  @Convert(converter = LocalDatePersistenceConverter.class)
  private LocalDate vencimento;
  @Column(name = "valor_titulo")
  private double valorTitulo;
  @Column(name = "valor_tarifa")
  private double valorTarifa;

  @ManyToOne
  @JoinColumn(name = "header_lote_id")
  private HeaderLote headerLote;

  @OneToOne(mappedBy = "registro", cascade = CascadeType.ALL)
  private RegistroDetalhe registroDetalhe;

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

  public LocalDate getVencimento() {
    return vencimento;
  }

  public void setVencimento(LocalDate vencimento) {
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
}
