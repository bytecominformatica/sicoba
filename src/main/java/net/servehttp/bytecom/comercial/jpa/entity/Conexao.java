package net.servehttp.bytecom.comercial.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.servehttp.bytecom.provedor.jpa.entity.Mikrotik;

@Entity
@Table(name = "conexao")
public class Conexao extends net.servehttp.bytecom.extra.jpa.entity.EntityGeneric {

  private static final long serialVersionUID = -4166003590731566705L;

  @OneToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "mikrotik_id")
  private Mikrotik mikrotik;

  private String nome;

  private String senha;

  public Conexao() {}

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSenha() {
    return this.senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Mikrotik getMikrotik() {
    return mikrotik;
  }

  public void setMikrotik(Mikrotik mikrotik) {
    this.mikrotik = mikrotik;
  }
}
