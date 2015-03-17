package net.servehttp.bytecom.extra.rest;

import java.io.Serializable;
import java.util.ArrayList;
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
public class ClientGeoRefServices implements Serializable {

  private static final long serialVersionUID = -1973794686416313717L;

  @Inject
  private ClienteGeoReferenciaJPA jpa;
  
  private ClientGeoRefPOJO clientpojo = new ClientGeoRefPOJO();
  private List<ClientGeoRefPOJO> listPojo = new ArrayList<ClientGeoRefPOJO>();

  private List<ClienteGeoReferencia> listClientes;

  @GET
  @Path("clientesGeo")
  public List<ClientGeoRefPOJO> listarClientes() {
    listClientes = jpa.buscarTodos(ClienteGeoReferencia.class);
    
    for (ClienteGeoReferencia clienteGeoReferencia : listClientes) {
      clientpojo.setNome(clienteGeoReferencia.getCliente().getNome());
      clientpojo.setLatitude((String)clienteGeoReferencia.getLatitude().toString());
      clientpojo.setLongitude((String)clienteGeoReferencia.getLongitude().toString());
      clientpojo.setStatus(clienteGeoReferencia.getCliente().getStatus());
      clientpojo.setEndereco(clienteGeoReferencia.getCliente().getEndereco());
      clientpojo.setNumero(clienteGeoReferencia.getCliente().getEndereco().getNumero());
      clientpojo.setFoneTitular(clienteGeoReferencia.getCliente().getFoneTitular());
      clientpojo.setFoneContato(clienteGeoReferencia.getCliente().getFoneContato());
      clientpojo.setContato(clienteGeoReferencia.getCliente().getContato());
      
      listPojo.add(clientpojo);
      
    }
    
    return listPojo;
  }

}
