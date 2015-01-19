package net.servehttp.bytecom.business;

import java.io.Serializable;

import net.servehttp.bytecom.persistence.entity.maps.ClienteGeoReferencia;

public class ClienteGeorefereciaBussiness extends genericoBusiness implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7214353784290694436L;
  
  public ClienteGeoReferencia findById(int id) {
    return genericoJPA.findById(ClienteGeoReferencia.class, id);
  }

}
