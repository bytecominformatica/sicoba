package net.servehttp.bytecom.provedor.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.servehttp.bytecom.extra.jpa.entity.EntityGeneric;

@Entity
@Table(name = "mikrotik")
public class Mikrotik extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -5454193409609731613L;

  @Lob
  private String descricao;
  private String nome;
  private String host;
  private int porta;
  private String usuario;
  private String senha;

  public Mikrotik() {
    this.usuario = "admin";
    this.porta = 8728;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getPorta() {
    return this.porta;
  }

  public void setPorta(int porta) {
    this.porta = porta;
  }

  public String getSenha() {
    return this.senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getUsuario() {
    return this.usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

}
