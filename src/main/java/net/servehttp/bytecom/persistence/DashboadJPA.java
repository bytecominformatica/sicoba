package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;
import net.servehttp.bytecom.persistence.entity.cadastro.QCliente;
import net.servehttp.bytecom.persistence.entity.cadastro.QContrato;
import net.servehttp.bytecom.persistence.entity.cadastro.QMensalidade;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusCliente;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusMensalidade;

import com.mysema.query.jpa.impl.JPAQuery;
import com.servehttp.bytecom.commons.DateUtil;

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


  public List<Cliente> buscarTodosClienteInstaladosRecente() {
    QCliente c = QCliente.cliente;
    return new JPAQuery(em).from(c).orderBy(c.contrato.dataInstalacao.desc()).limit(10).list(c);
  }


  public long getQuantidadeInstalacoesDoMes() {
    QContrato c = QContrato.contrato;
    Date data = DateUtil.getPrimeiroDiaDoMes().getTime();
    return new JPAQuery(em).from(c).where(c.dataInstalacao.goe(data)).count();
  }

  public List<Mensalidade> getMensalidadesEmAtraso() {
    QMensalidade m = QMensalidade.mensalidade;
    Date data = DateUtil.getHoje();
    return new JPAQuery(em).from(m)
        .where(m.status.eq(StatusMensalidade.PENDENTE).and(m.dataVencimento.lt(data)))
        .orderBy(m.dataVencimento.desc()).list(m);
  }

  public double getFaturamentoDoMes() {
    Double d =
        em.createQuery(
            "select sum(m.valorPago) from Mensalidade m where m.dataOcorrencia between :inicio and :fim",
            Double.class).setParameter("inicio", DateUtil.getPrimeiroDiaDoMes().getTime())
            .setParameter("fim", DateUtil.getUltimoDiaDoMes().getTime()).getSingleResult();

    return d != null ? d : 0;
  }

  public double getFaturamentoPrevistoDoMes() {
    Double d =
        em.createQuery(
            "select sum(m.valor) from Mensalidade m where m.dataVencimento between :inicio and :fim",
            Double.class).setParameter("inicio", DateUtil.getPrimeiroDiaDoMes().getTime())
            .setParameter("fim", DateUtil.getUltimoDiaDoMes().getTime()).getSingleResult();
    return d != null ? d : 0;
  }

  public List<Cliente> getClientesInativos() {
    QCliente c = QCliente.cliente;
    return new JPAQuery(em).from(c).where(c.status.eq(StatusCliente.INATIVO)).orderBy(c.nome.asc()).list(c);
  }

  public List<Cliente> getClientesSemMensalidade() {
    return em
        .createQuery(
            "select c from Cliente c where (c.status = :status1 or c.status = :status2) and c.id not in(select DISTINCT(m.cliente.id) from Mensalidade m where m.dataVencimento > :hoje)",
            Cliente.class).setParameter("status1", StatusCliente.ATIVO)
        .setParameter("status2", StatusCliente.INATIVO).setParameter("hoje", new Date())
        .getResultList();
  }
}
