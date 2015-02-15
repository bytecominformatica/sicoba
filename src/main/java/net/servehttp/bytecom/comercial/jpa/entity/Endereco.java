package net.servehttp.bytecom.comercial.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.servehttp.bytecom.extra.jpa.entity.EntityGeneric;

@Entity
@Table(name = "endereco")
@NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e")
public class Endereco extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -1834900327044240105L;
  @NotNull(message = "cep é obrigatório")
  private String cep;
  @NotNull(message = "logradouro é obrigatório")
  private String logradouro;
  private String numero;
  private String complemento;

  @NotNull(message = "bairro é obrigatório")
  @ManyToOne(fetch = FetchType.EAGER)
  private Bairro bairro;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getComplemento() {
    return this.complemento;
  }

  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  public String getLogradouro() {
    return this.logradouro;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public String getNumero() {
    return this.numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public Bairro getBairro() {
    return this.bairro;
  }

  public void setBairro(Bairro bairro) {
    this.bairro = bairro;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(logradouro).append(", ").append(numero);
    if (complemento != null && !complemento.isEmpty()) {
      sb.append(", ").append(complemento);
    }
    if (bairro != null) {
      sb.append(", ").append(bairro.getNome()).append(", ").append(bairro.getCidade())
          .append(" - ").append(bairro.getCidade().getEstado().getUf());
    }
    return sb.toString();
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }
}
