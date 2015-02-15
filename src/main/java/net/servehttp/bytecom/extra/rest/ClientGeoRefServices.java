package net.servehttp.bytecom.extra.rest;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.servehttp.bytecom.extra.jpa.entity.ClienteGeoReferencia;
import net.servehttp.bytecom.extra.service.ClienteGeorefereciaBussiness;

@Path("maps")
public class ClientGeoRefServices implements Serializable {

  private static final long serialVersionUID = -1832169207767594262L;

  @Inject 
  private ClienteGeorefereciaBussiness clienteGeoBussiness;

  private List<ClienteGeoReferencia> listClientes;

  
  @GET
  @Path("clientesGeo")
  @Produces("application/json")
  public ClientGeoRefServices getClientes() {
    listClientes = clienteGeoBussiness.buscar();
    return (ClientGeoRefServices) listClientes;
    
  }

}
