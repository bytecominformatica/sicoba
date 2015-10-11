package net.servehttp.bytecom.persistence.jpa.estoque;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.jpa.entity.estoque.Equipamento;
import net.servehttp.bytecom.persistence.jpa.entity.estoque.QEquipamento;
import net.servehttp.bytecom.persistence.jpa.entity.estoque.StatusEquipamento;
import net.servehttp.bytecom.persistence.jpa.entity.estoque.TipoEquipamento;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author clairton
 */
@Transactional
public class EquipamentoJPA implements Serializable {

  private static final long serialVersionUID = 7528131197866761853L;
  @Inject
  protected EntityManager em;
  private QEquipamento e = QEquipamento.equipamento;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Equipamento> buscarTodosEquipamento() {
    return new JPAQuery(em).from(e).limit(200).orderBy(e.id.desc()).list(e);
  }

  public List<Equipamento> buscarEquipamentosNaoUtilizados(TipoEquipamento tipo,
      StatusEquipamento status) {
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

  public Equipamento buscarPorId(int id) {
    return new JPAQuery(em).from(e).where(e.id.eq(id)).uniqueResult(e);
  }

  public Equipamento buscarEquipamentoPorMac(String mac) {
    return new JPAQuery(em).from(e).where(e.mac.eq(mac)).uniqueResult(e);
  }

}
