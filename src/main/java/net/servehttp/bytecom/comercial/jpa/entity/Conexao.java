package net.servehttp.bytecom.comercial.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.servehttp.bytecom.provedor.jpa.entity.Mikrotik;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "conexao")
public class Conexao extends net.servehttp.bytecom.extra.jpa.entity.EntityGeneric {

  private static final long serialVersionUID = -4166003590731566705L;

  @OneToOne
  @JoinColumn(name = "cliente_id")
  @XmlTransient
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "mikrotik_id")
  private Mikrotik mikrotik;

  private String nome;

  private String senha;

  public Conexao() {}
  
  public String getProfile(){
    return cliente.getStatus().getProfile(cliente);
  }

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
