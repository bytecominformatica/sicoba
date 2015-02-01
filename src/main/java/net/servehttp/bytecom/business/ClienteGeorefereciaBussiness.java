package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.ClienteGeoReferenciaJPA;
import net.servehttp.bytecom.persistence.entity.maps.ClienteGeoReferencia;

public class ClienteGeorefereciaBussiness extends genericoBusiness implements Serializable {

  @Inject
  private ClienteGeoReferenciaJPA clientegeoJPA;
  
  private static final long serialVersionUID = -7214353784290694436L;
  
  public ClienteGeoReferencia findById(int id) {
    return genericoJPA.findById(ClienteGeoReferencia.class, id);
  }
  
  public List<ClienteGeoReferencia> listaClientesGeorefs(){
    return clientegeoJPA.buscaClientesGeo();
  }
  
  public List<ClienteGeoReferencia> buscar(){
    return genericoJPA.buscarTodos(ClienteGeoReferencia.class);
  }

}
