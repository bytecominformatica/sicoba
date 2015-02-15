package net.servehttp.bytecom.financeiro.jpa;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import net.servehttp.bytecom.extra.jpa.GenericoJPA;
import net.servehttp.bytecom.financeiro.jpa.entity.Mensalidade;
import net.servehttp.bytecom.financeiro.jpa.entity.StatusMensalidade;

@Transactional
public class MensalidadeRelatorioJPA extends GenericoJPA {

  private static final long serialVersionUID = -666959135258997285L;

  public List<Mensalidade> buscarPorDataStatus(LocalDate inicio, LocalDate fim, StatusMensalidade status, boolean buscarPorDataOcorrencia) {

    String jpql;
    if(buscarPorDataOcorrencia){
      jpql = "select m from Mensalidade m where m.dataOcorrencia between :inicio and :fim ";
    } else {
      jpql = "select m from Mensalidade m where m.dataVencimento between :inicio and :fim ";  
    }
    
    if (status != null) {
      jpql += "and m.status = :status ";
    }
    
    if(buscarPorDataOcorrencia){
      jpql += "order by m.dataOcorrencia, m.dataVencimento desc ";
    } else {
      jpql += "order by m.dataVencimento, m.dataOcorrencia desc ";
    }

    TypedQuery<Mensalidade> query =
        em.createQuery(jpql, Mensalidade.class).setParameter("inicio", inicio)
            .setParameter("fim", fim);
    
    if (status != null) {
      query.setParameter("status", status);
    }
    
    return query.getResultList();
  }

  public void setEntityManager(EntityManager em2) {
    this.em = em2;
  }
}
