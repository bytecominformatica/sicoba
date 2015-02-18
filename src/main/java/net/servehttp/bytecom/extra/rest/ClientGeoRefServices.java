package net.servehttp.bytecom.extra.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.servehttp.bytecom.extra.jpa.entity.ClienteGeoReferencia;
import net.servehttp.bytecom.extra.service.ClienteGeorefereciaBussiness;

@Path("maps")
public class ClientGeoRefServices {

  @Inject
  ClienteGeorefereciaBussiness clienteGeoBusssiness;
  
  private List<ClienteGeoReferencia> listClientes;
  
  @GET
  @Path("clientesGeo")
  @Produces("application/json")
  public List<ClienteGeoReferencia> listarClientes(){
    listClientes = clienteGeoBusssiness.buscar();
    return listClientes;
  }
  
  @GET
  @Path("merda")
  public String teste(){
    return "Merda";
  }

  public List<ClienteGeoReferencia> getListClientes() {
    return listClientes;
  }

  public void setListClientes(List<ClienteGeoReferencia> listClientes) {
    this.listClientes = listClientes;
  }
  
  
   

}
