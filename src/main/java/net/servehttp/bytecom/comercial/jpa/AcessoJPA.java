package net.servehttp.bytecom.comercial.jpa;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.comercial.jpa.entity.Acesso;
import net.servehttp.bytecom.comercial.jpa.entity.QAcesso;
import net.servehttp.bytecom.extra.jpa.GenericoJPA;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author clairton
 */
@Transactional
public class AcessoJPA extends GenericoJPA {

  private static final long serialVersionUID = -5261815050659537292L;
  private QAcesso a = QAcesso.acesso;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public String getIpLivre() {
    String rede = "10.10.0.";
    String ipLivre = null;
    for (int i = 10; i <= 250; i++) {
      Acesso result = new JPAQuery(em).from(a).where(a.ip.eq(rede + i)).uniqueResult(a);
      if (result == null) {
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

  public Acesso buscarAcessoPorIp(String ip) {
    return new JPAQuery(em).from(a).where(a.ip.eq(ip)).uniqueResult(a);
  }

}
