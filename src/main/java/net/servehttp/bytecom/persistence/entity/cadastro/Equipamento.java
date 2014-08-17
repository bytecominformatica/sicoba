package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Clairton
 */
@Entity
@Table(name = "equipamento")
public class Equipamento extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -6708003996308589556L;
  public static final int STATUS_OK = 0;
  public static final int STATUS_DEFEITO = 1;

  public static final int TIPO_INSTALACAO = 0;
  public static final int TIPO_WIFI = 1;

  private String descricao;
  @Size(min = 1, max = 30)
  private String marca;
  @Size(min = 1, max = 30)
  private String modelo;
  @Size(min = 1, max = 20)
  @Pattern(
      regexp = "^[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}$",
      message = "MAC inválido")
  private String mac;
  private int tipo;
  private int status;

  public Equipamento() {}

  public Equipamento(Integer id) {
    this.setId(id);
  }

  public Equipamento(Integer id, String marca, String modelo, String mac, Calendar updatedAt) {
    this.setId(id);
    this.marca = marca;
    this.modelo = modelo;
    this.mac = mac;
    this.updatedAt = updatedAt;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca != null ? marca.toUpperCase() : marca;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo != null ? modelo.toUpperCase() : modelo;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac != null ? mac.toUpperCase() : mac;
  }

  public int getTipo() {
    return tipo;
  }

  public void setTipo(int tipo) {
    this.tipo = tipo;
  }

  public int getTIPO_INSTALACAO() {
    return TIPO_INSTALACAO;
  }

  public int getTIPO_WIFI() {
    return TIPO_WIFI;
  }

  public int getSTATUS_OK() {
    return STATUS_OK;
  }

  public int getSTATUS_DEFEITO() {
    return STATUS_DEFEITO;
  }

  @Override
  public String toString() {
    return modelo + " - " + mac;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

}
