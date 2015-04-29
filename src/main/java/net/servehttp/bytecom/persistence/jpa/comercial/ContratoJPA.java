package net.servehttp.bytecom.persistence.jpa.comercial;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Contrato;

/**
 * 
 * @author clairton
 */
@Transactional
public class ContratoJPA implements Serializable {

  private static final long serialVersionUID = -2556507568580609030L;
  @Inject
  protected EntityManager em;
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  public void remover(Contrato c) {
    em.createQuery("delete from Contrato c where c.id  = :id").setParameter("id", c.getId())
        .executeUpdate();
  }

}
