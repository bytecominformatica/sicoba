package net.servehttp.bytecom.comercial.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.servehttp.bytecom.extra.jpa.entity.EntityGeneric;


@Entity
@Table(name = "bairro")
@NamedQuery(name = "Bairro.findAll", query = "SELECT b FROM Bairro b")
public class Bairro extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = 4219357783343963670L;

  private String nome;

  @ManyToOne(fetch = FetchType.EAGER)
  private Cidade cidade;

  public Bairro() {}

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Cidade getCidade() {
    return this.cidade;
  }

  public void setCidade(Cidade cidade) {
    this.cidade = cidade;
  }
}
