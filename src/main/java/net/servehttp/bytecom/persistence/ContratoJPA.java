package net.servehttp.bytecom.persistence;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.cadastro.Contrato;

/**
 * 
 * @author clairton
 */
@Transactional
public class ContratoJPA extends GenericoJPA {

  private static final long serialVersionUID = -2556507568580609030L;
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  public void remover(Contrato c) {
    em.createQuery("delete from Contrato c where c.id  = :id").setParameter("id", c.getId())
        .executeUpdate();
  }

}
