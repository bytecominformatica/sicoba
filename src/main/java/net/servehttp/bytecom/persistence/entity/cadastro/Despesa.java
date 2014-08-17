package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author felipe
 *
 */
@Entity
@Table(name = "despesa")
public class Despesa extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -855139058972577589L;
  private String descricao;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "fornecedor_id")
  private Fornecedor fornecedor;
  private double valor;
  private Date data;
  private char status;

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Fornecedor getFornecedor() {
    return fornecedor;
  }

  public void setFornecedor(Fornecedor fornecedor) {
    this.fornecedor = fornecedor;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public char getStatus() {
    return status;
  }

  public void setStatus(char status) {
    this.status = status;
  }

}
