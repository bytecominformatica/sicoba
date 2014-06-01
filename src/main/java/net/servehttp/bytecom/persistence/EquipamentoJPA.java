package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Equipamento;

/**
 * 
 * @author clairton
 */
@Transactional
public class EquipamentoJPA implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7528131197866761853L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Equipamento> buscaEquipamentosNaoUtilizados(int tipo, int status) {
    List<Equipamento> list;
    String jpql;
    if (tipo == Equipamento.TIPO_INSTALACAO) {
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

  public Equipamento buscarEquipamentoPorMac(String mac) {
    try {
      return em.createQuery("select e from Equipamento e where e.mac = :mac", Equipamento.class)
          .setParameter("mac", mac).getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      return null;
    }
  }

  /**
   * Verifica se o MAC já existe.
   * 
   * @return boolean
   */
  public boolean existMAC(String mac) {
    Equipamento e = buscarEquipamentoPorMac(mac);
    if (e == null) {
      return false;
    }
    return true;
  }

}
