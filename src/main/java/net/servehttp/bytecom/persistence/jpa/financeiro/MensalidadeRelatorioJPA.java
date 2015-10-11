package net.servehttp.bytecom.persistence.jpa.financeiro;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Mensalidade;

@Transactional
public class MensalidadeRelatorioJPA implements Serializable {

  private static final long serialVersionUID = -666959135258997285L;
  @Inject
  protected EntityManager em;

  public List<Mensalidade> buscarPorData(LocalDate inicio, LocalDate fim, boolean buscarPorDataOcorrencia) {
//
//    String jpql;
//    if(buscarPorDataOcorrencia){
//      jpql = "select m from Mensalidade m where m.dataOcorrencia between :inicio and :fim ";
//    } else {
//      jpql = "select m from Mensalidade m where m.dataVencimento between :inicio and :fim ";
//    }
//
//    if(buscarPorDataOcorrencia){
//      jpql += "order by m.dataOcorrencia, m.dataVencimento desc ";
//    } else {
//      jpql += "order by m.dataVencimento, m.dataOcorrencia desc ";
//    }
//
//    TypedQuery<Mensalidade> query =
//        em.createQuery(jpql, Mensalidade.class).setParameter("inicio", inicio)
//            .setParameter("fim", fim);
//
//    return query.getResultList();
    return new ArrayList<>();
  }

  public void setEntityManager(EntityManager em2) {
    this.em = em2;
  }
}
