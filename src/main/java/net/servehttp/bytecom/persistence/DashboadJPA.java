package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Mensalidade;
import net.servehttp.bytecom.util.DateUtil;

/**
 * 
 * @author clairton
 */
@Transactional
public class DashboadJPA implements Serializable {

  private static final long serialVersionUID = 4057406973170798760L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public long getQuantidadeInstalacoesDoMes(){
    return em.createQuery("select count(c.id) from Contrato c where c.dataInstalacao >= :date", Long.class)
        .setParameter("date", DateUtil.INSTANCE.getPrimeiroDiaDoMes().getTime()).getSingleResult();
  }

  public List<Mensalidade> getMensalidadesEmAtraso(){
    return em.createQuery("select m from Mensalidade m where m.status = :status and m.dataVencimento < :date order by m.dataVencimento desc", Mensalidade.class)
        .setParameter("status", Mensalidade.NAO_PAGA)
        .setParameter("date", DateUtil.INSTANCE.getHoje())
        .getResultList();
  }

  public double getFaturamentoDoMes(){
    return em.createQuery("select sum(m.valorPago) from Mensalidade m where m.dataVencimento between :inicio and :fim", Double.class)
        .setParameter("inicio", DateUtil.INSTANCE.getPrimeiroDiaDoMes().getTime())
        .setParameter("fim", DateUtil.INSTANCE.getUltimoDiaDoMes().getTime())
        .getSingleResult();
  }

  public double getFaturamentoPrevistoDoMes() {
    return em.createQuery("select sum(m.valor) from Mensalidade m where m.dataVencimento between :inicio and :fim", Double.class)
        .setParameter("inicio", DateUtil.INSTANCE.getPrimeiroDiaDoMes().getTime())
        .setParameter("fim", DateUtil.INSTANCE.getUltimoDiaDoMes().getTime())
        .getSingleResult();
    
  }

}
