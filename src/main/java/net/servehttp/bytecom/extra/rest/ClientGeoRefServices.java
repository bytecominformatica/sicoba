package net.servehttp.bytecom.extra.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.servehttp.bytecom.extra.jpa.ClienteGeoReferenciaJPA;
import net.servehttp.bytecom.extra.jpa.entity.ClienteGeoReferencia;

@Path("maps")
@Produces(MediaType.APPLICATION_JSON)
public class ClientGeoRefServices {

  @Inject
  private ClienteGeoReferenciaJPA jpa;

  private List<ClienteGeoReferencia> listClientes;

  @GET
  @Path("clientesGeo")
  public List<ClienteGeoReferencia> listarClientes() {
    listClientes = jpa.buscarTodos(ClienteGeoReferencia.class);
    return listClientes;
  }

  @GET
  @Path("merda")
  public String teste() {
    return "Merda";
  }

  public List<ClienteGeoReferencia> getListClientes() {
    return listClientes;
  }

  public void setListClientes(List<ClienteGeoReferencia> listClientes) {
    this.listClientes = listClientes;
  }

}
