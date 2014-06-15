package net.servehttp.bytecom.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "acesso")
public class Acesso implements Serializable {

  @Transient
  public final static int INATIVO = 0;
  @Transient
  public final static int ATIVO = 1;
  @Transient
  public final static int CANCELADO = 2;

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Pattern(
      regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
      message = "IP inválido")
  @Size(min = 1, max = 20)
  private String ip;
  @Pattern(
      regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
      message = "Mascara inválido")
  @Size(min = 1, max = 20)
  private String mascara;
  @Pattern(
      regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
      message = "Gateway inválido")
  @Size(min = 1, max = 20)
  private String gateway;
  @Pattern(
      regexp = "^[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}$",
      message = "MAC inválido")
  @Size(min = 1, max = 20)
  private String mac;
  private int status;
  @JoinColumn(name = "cliente_id", referencedColumnName = "id")
  @OneToOne(optional = false)
  private Cliente cliente;
  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  public Acesso() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getMascara() {
    return mascara;
  }

  public void setMascara(String mascara) {
    this.mascara = mascara;
  }

  public String getGateway() {
    return gateway;
  }

  public void setGateway(String gateway) {
    this.gateway = gateway;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
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

  public int getINATIVO() {
    return INATIVO;
  }

  public int getATIVO() {
    return ATIVO;
  }

  public int getCANCELADO() {
    return CANCELADO;
  }

  public String getStatusFormatado() {
    switch (status) {
      case 0:
        return "INATIVO";
      case 1:
        return "ATIVO";
      case 2:
        return "CANCELADO";
      default:
        return "";
    }
  }

}
