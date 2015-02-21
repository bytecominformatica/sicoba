package net.servehttp.bytecom.extra.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.extra.jpa.entity.ClienteGeoReferencia;
import net.servehttp.bytecom.extra.jpa.entity.QClienteGeoReferencia;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author Felipe W. M. Martins
 *
 */
@Transactional
public class ClienteGeoReferenciaJPA extends GenericoJPA implements Serializable {

  private static final long serialVersionUID = 7468802847947425443L;
  private QClienteGeoReferencia cg = QClienteGeoReferencia.clienteGeoReferencia;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<ClienteGeoReferencia> buscaClientesGeo() {
    List<ClienteGeoReferencia> listClientes = buscarTodos(ClienteGeoReferencia.class);
    return listClientes;
  }

  public ClienteGeoReferencia buscarClienteGeoReferenciaPorCliente(Cliente cliente) {
    return new JPAQuery(em).from(cg).where(cg.cliente.id.eq(cliente.getId())).uniqueResult(cg);
  }



}
