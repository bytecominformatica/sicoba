package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.cadastro.Equipamento;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusEquipamento;
import net.servehttp.bytecom.persistence.entity.cadastro.TipoEquipamento;

/**
 * 
 * @author clairton
 */
@Transactional
public class EquipamentoJPA implements Serializable {

  private static final long serialVersionUID = 7528131197866761853L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Equipamento> buscarEquipamentosNaoUtilizados(TipoEquipamento tipo, StatusEquipamento status) {
    List<Equipamento> list;
    String jpql;
    if (tipo == TipoEquipamento.INSTALACAO) {
      jpql =
          "select e from Equipamento e where e.tipo = :tipo and e.status = :status and e.id not in (select c.equipamento.id from Contrato c where c.equipamento.id is not null) order by e.modelo)";
    } else {
      jpql =
          "select e from Equipamento e where e.tipo = :tipo and e.status = :status and e.id not in (select c.equipamentoWifi.id from Contrato c where c.equipamentoWifi.id is not null) order by e.modelo)";
    }
    list =
        em.createQuery(jpql, Equipamento.class).setParameter("tipo", tipo)
            .setParameter("status", status).getResultList();

    return list;
  }

  public void remover(Equipamento e) {
    em.createQuery("delete from Equipamento e where e.id  = :id").setParameter("id", e.getId())
    .executeUpdate();
  }

}
