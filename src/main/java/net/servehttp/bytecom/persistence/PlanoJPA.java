package net.servehttp.bytecom.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.cadastro.Plano;
import net.servehttp.bytecom.persistence.entity.cadastro.QPlano;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 *
 * @author clairton
 */
@Transactional
public class PlanoJPA {

  private QPlano p = QPlano.plano;
  @Inject
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Plano> buscarPlanoPorNome(String pesquisa) {
    return new JPAQuery(em).from(p).where(p.nome.like("pesquisa" + "%")).list(p);
  }
}
