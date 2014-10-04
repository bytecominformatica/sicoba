package net.servehttp.bytecom.persistence.entity.maps;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.EntityGeneric;

/**
 * 
 * @author Felipe W. M. Martins
 *
 */

@Entity
@Table(name = "cliente_georeferencia")
public class ClienteGeoReferencia extends EntityGeneric {

  private static final long serialVersionUID = -7721922565148704991L;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;
  
  @Column(name = "lat")
  private BigDecimal latitude;
  
  @Column(name = "lng")
  private BigDecimal longitude;

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }
  
  

}
