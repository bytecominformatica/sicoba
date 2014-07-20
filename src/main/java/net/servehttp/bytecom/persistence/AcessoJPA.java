package net.servehttp.bytecom.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Acesso;

/**
 * 
 * @author clairton
 */
@Transactional
public class AcessoJPA implements Serializable {

  private static final long serialVersionUID = -5261815050659537292L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public String getIpLivre() {
    String rede = "10.10.0.";
    String ipLivre = null;
    for (int i = 10; i <= 250; i++) {
      try {
        Acesso a = em.createQuery("select a from Acesso a where a.ip = :ip", Acesso.class)
                .setParameter("ip", rede + i).getSingleResult();
      } catch (NoResultException e) {
        ipLivre = rede + i;
        break;
      }

    }

    return ipLivre;
  }

}
