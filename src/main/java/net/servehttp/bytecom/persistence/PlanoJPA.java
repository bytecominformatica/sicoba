package net.servehttp.bytecom.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import net.servehttp.bytecom.persistence.entity.cadastro.Plano;
import net.servehttp.bytecom.persistence.entity.cadastro.QPlano;

/**
 *
 * @author clairton
 */
@Transactional
public class PlanoJPA {

  private QPlano p = QPlano.plano;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Plano> buscarPlanoPorNome(String pesquisa) {
    return new JPAQuery(em).from(p).where(p.nome.like("pesquisa" + "%")).list(p);
  }
}
