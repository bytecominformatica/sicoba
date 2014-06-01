package net.servehttp.bytecom.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Acesso;

/**
 * 
 * @author Clairton Luz
 * 
 */
@Transactional
public class AcessoJPA implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4606879676470472941L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  /**
   * retorna o acesso referente ao IP passado como parametro.
   * 
   * @param ip
   * @return Acesso
   */
  public Acesso buscaPorIp(String ip) {
    try {
      return em.createQuery("select a from Acesso a where a.ip = :ip", Acesso.class)
          .setParameter("ip", ip).getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      return null;
    }
  }

  /**
   * retorna o acesso referente ao MAC passado como parametro.
   * 
   * @param mac
   * @return Acesso
   */
  public Acesso buscaPorMac(String mac) {
    try {
      return em.createQuery("select a from Acesso a where a.mac = :mac", Acesso.class)
          .setParameter("mac", mac).getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      return null;
    }
  }
}
