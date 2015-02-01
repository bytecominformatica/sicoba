package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
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

  private String descricao;
  @Size(min = 1, max = 30)
  private String marca;
  @Size(min = 1, max = 30)
  private String modelo;
  @Pattern(
      regexp = "^[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}$",
      message = "MAC inválido")
  private String mac;
  @Enumerated
  private TipoEquipamento tipo;
  @Enumerated
  private StatusEquipamento status;

  public Equipamento() {}

  public Equipamento(Integer id) {
    this.setId(id);
  }

  public Equipamento(Integer id, String marca, String modelo, String mac, LocalDateTime updatedAt) {
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

  public TipoEquipamento getTipo() {
    return tipo;
  }

  public void setTipo(TipoEquipamento tipo) {
    this.tipo = tipo;
  }

  public StatusEquipamento getStatus() {
    return status;
  }

  public void setStatus(StatusEquipamento status) {
    this.status = status;
  }

}
