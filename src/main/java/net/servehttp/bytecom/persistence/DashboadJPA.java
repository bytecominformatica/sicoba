package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
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

import com.mysema.query.jpa.JPASubQuery;
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
  private Date from;
  private Date to;

  public DashboadJPA() {
    from = DateUtil.getPrimeiroDiaDoMes().getTime();
    to = DateUtil.getUltimoDiaDoMes().getTime();
  }

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
        .orderBy(m.dataVencimento.asc()).list(m);
  }

  public double getFaturamentoDoMes() {
    QMensalidade m = QMensalidade.mensalidade;
    Double d =
        new JPAQuery(em).from(m).where(m.dataOcorrencia.between(from, to))
            .uniqueResult(m.valorPago.sum());
    return d != null ? d : 0;
  }

  public double getFaturamentoPrevistoDoMes() {
    QMensalidade m = QMensalidade.mensalidade;
    Double d =
        new JPAQuery(em).from(m).where(m.dataVencimento.between(from, to))
            .uniqueResult(m.valor.sum());
    return d != null ? d : 0;
  }

  public List<Cliente> getClientesInativos() {
    QCliente c = QCliente.cliente;
    return new JPAQuery(em).from(c).where(c.status.eq(StatusCliente.INATIVO)).orderBy(c.nome.asc())
        .list(c);
  }

  public List<Cliente> getClientesSemMensalidade() {
    QCliente c = QCliente.cliente;
    QMensalidade m = QMensalidade.mensalidade;
    Date data =
        Date.from(LocalDate.now().plusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault())
            .toInstant());
    return new JPAQuery(em)
        .from(c)
        .where(
            (c.status.eq(StatusCliente.ATIVO).or(c.status.eq(StatusCliente.INATIVO)).and(c.id
                .notIn(new JPASubQuery().from(m).distinct().where(m.dataVencimento.gt(data))
                    .list(m.cliente.id))))).list(c);
  }
}
