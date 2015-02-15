package net.servehttp.bytecom.extra.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.extra.jpa.entity.ClienteGeoReferencia;
import net.servehttp.bytecom.extra.jpa.entity.QClienteGeoReferencia;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author Felipe W. M. Martins
 *
 */
@Transactional
public class ClienteGeoReferenciaJPA extends GenericoJPA {

  private static final long serialVersionUID = 7468802847947425443L;
  private QClienteGeoReferencia cg = QClienteGeoReferencia.clienteGeoReferencia;
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  public List<ClienteGeoReferencia> buscaClientesGeo(){
    return new JPAQuery(em)
              .from(cg).list(cg);
  }
  
  
  

}
