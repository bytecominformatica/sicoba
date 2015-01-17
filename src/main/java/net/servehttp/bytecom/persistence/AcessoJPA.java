package net.servehttp.bytecom.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.cadastro.Acesso;
import net.servehttp.bytecom.persistence.entity.cadastro.QAcesso;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author clairton
 */
@Transactional
public class AcessoJPA implements Serializable {

  private static final long serialVersionUID = -5261815050659537292L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;
  private QAcesso a = QAcesso.acesso;
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public String getIpLivre() {
    String rede = "10.10.0.";
    String ipLivre = null;
    for (int i = 10; i <= 250; i++) {
      Acesso result = new JPAQuery(em).from(a).where(a.ip.eq(rede + i)).uniqueResult(a);
      if(result == null) {
        ipLivre = rede + i;
        break;
      }
    }

    return ipLivre;
  }
  
  public void remover(Acesso a) {
    em.createQuery("delete from Acesso m where m.id  = :id").setParameter("id", a.getId())
        .executeUpdate();
  }

}
