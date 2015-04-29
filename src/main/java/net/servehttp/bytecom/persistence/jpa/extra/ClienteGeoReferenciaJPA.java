package net.servehttp.bytecom.persistence.jpa.extra;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.extra.ClienteGeoReferencia;
import net.servehttp.bytecom.persistence.jpa.entity.extra.QClienteGeoReferencia;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author Felipe W. M. Martins
 *
 */
@Transactional
public class ClienteGeoReferenciaJPA implements Serializable {

  private static final long serialVersionUID = 7468802847947425443L;
  @Inject
  protected EntityManager em;
  private QClienteGeoReferencia cg = QClienteGeoReferencia.clienteGeoReferencia;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public ClienteGeoReferencia buscarClienteGeoReferenciaPorCliente(Cliente cliente) {
    return new JPAQuery(em).from(cg).where(cg.cliente.id.eq(cliente.getId())).uniqueResult(cg);
  }



}
