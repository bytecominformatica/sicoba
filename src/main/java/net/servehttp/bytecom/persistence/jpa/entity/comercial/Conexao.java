package net.servehttp.bytecom.persistence.jpa.entity.comercial;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.IConnectionClienteCertified;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.Mikrotik;

@Entity
@Table(name = "conexao")
public class Conexao extends EntityGeneric implements IConnectionClienteCertified {

  private static final long serialVersionUID = -4166003590731566705L;

  @OneToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "mikrotik_id")
  private Mikrotik mikrotik;

  private String nome;

  private String senha;

  @Pattern(
      regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
      message = "IP inválido")
  @Size(min = 1, max = 50)
  private String ip;

  public Conexao() {}

  public String getProfile() {
    Optional<Cliente> c = Optional.ofNullable(cliente);
    return c.map(Cliente::getContrato).map(Contrato::getPlano).map(Plano::getNome).orElse(null);
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

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  @Override
  public String getMac() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getLogin() {
    return nome;
  }

  @Override
  public String getPass() {
    return senha;
  }
}
