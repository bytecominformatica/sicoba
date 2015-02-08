package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.ClienteGeoReferenciaJPA;
import net.servehttp.bytecom.persistence.entity.maps.ClienteGeoReferencia;

public class ClienteGeorefereciaBussiness implements Serializable {

  @Inject
  private ClienteGeoReferenciaJPA clientegeoJPA;

  private static final long serialVersionUID = -7214353784290694436L;

  public ClienteGeoReferencia buscarPorId(int id) {
    return clientegeoJPA.buscarPorId(ClienteGeoReferencia.class, id);
  }

  public List<ClienteGeoReferencia> listaClientesGeorefs() {
    return clientegeoJPA.buscaClientesGeo();
  }

  public List<ClienteGeoReferencia> buscar() {
    return clientegeoJPA.buscarTodos(ClienteGeoReferencia.class);
  }

  public <T> T salvar(T t) {
    return clientegeoJPA.salvar(t);
  }

  public <T> T atualizar(T t) {
    return clientegeoJPA.atualizar(t);
  }


}
