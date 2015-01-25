package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;
import net.servehttp.bytecom.persistence.entity.pingtest.QPontoTransmissao;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 *
 * @author clairton
 */
@Transactional
public class PontoTransmissaoJPA implements Serializable {

  private static final long serialVersionUID = 5006498719314183836L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;
  private QPontoTransmissao p = QPontoTransmissao.pontoTransmissao;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<PontoTransmissao> buscarTodosPontoTransmissaoRecebeDeNull() {
    return new JPAQuery(em).from(p).where(p.recebeDe.isNotNull()).list(p);
  }

}
