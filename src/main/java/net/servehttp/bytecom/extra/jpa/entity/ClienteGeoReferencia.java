package net.servehttp.bytecom.extra.jpa.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;

/**
 * 
 * @author Felipe W. M. Martins
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "cliente_georeferencia")
public class ClienteGeoReferencia extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -7721922565148704991L;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
  @JoinColumn(name = "cliente_id")
  @XmlTransient
  private Cliente cliente;
  
  @Column(name = "lat")
  private Double latitude;
  
  @Column(name = "lng")
  private Double longitude;

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }
  

}
