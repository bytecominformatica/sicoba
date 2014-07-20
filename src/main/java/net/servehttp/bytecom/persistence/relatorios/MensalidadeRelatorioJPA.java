package net.servehttp.bytecom.persistence.relatorios;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Mensalidade;

@Transactional
public class MensalidadeRelatorioJPA implements Serializable {

  private static final long serialVersionUID = -666959135258997285L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public List<Mensalidade> buscarPorDataStatus(Date inicio, Date fim, int status, boolean buscarPorDataOcorrencia) {

    String jpql;
    if(buscarPorDataOcorrencia){
      jpql = "select m from Mensalidade m where m.dataOcorrencia between :inicio and :fim ";
    } else {
      jpql = "select m from Mensalidade m where m.dataVencimento between :inicio and :fim ";  
    }
    
    if (status != -1) {
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
    
    if (status != -1) {
      query.setParameter("status", status);
    }
    
    return query.getResultList();
  }

  public void setEntityManager(EntityManager em2) {
    this.em = em2;
  }
}
