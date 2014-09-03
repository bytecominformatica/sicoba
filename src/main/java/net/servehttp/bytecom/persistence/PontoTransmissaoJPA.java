package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;

/**
 *
 * @author clairton
 */
@Transactional
public class PontoTransmissaoJPA implements Serializable{

  private static final long serialVersionUID = 5006498719314183836L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<PontoTransmissao> buscarTodosPontoTransmissaoInicial() {
    return em.createQuery("select pt from PontoTransmissao pt where pt.recebeDe is null",
        PontoTransmissao.class).getResultList();
  }
}
