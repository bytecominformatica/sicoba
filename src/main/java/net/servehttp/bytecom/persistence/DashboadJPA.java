package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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

/**
 * 
 * @author clairton
 */
@Transactional
public class DashboadJPA implements Serializable {

  private static final long serialVersionUID = 4057406973170798760L;
  @Inject
  private EntityManager em;
  private LocalDate from;
  private LocalDate to;

  public DashboadJPA() {
    from = LocalDate.now().withDayOfMonth(1);
    to = LocalDate.now().withDayOfMonth(1);
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
    LocalDate data = LocalDate.now().withDayOfMonth(1);
    // Date data = DateUtil.getPrimeiroDiaDoMes().getTime();
    return new JPAQuery(em).from(c).where(c.dataInstalacao.goe(data)).count();
  }

  public List<Mensalidade> getMensalidadesEmAtraso() {
    QMensalidade m = QMensalidade.mensalidade;
    return new JPAQuery(em).from(m)
        .where(m.status.eq(StatusMensalidade.PENDENTE).and(m.dataVencimento.lt(LocalDate.now())))
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
    LocalDate data = LocalDate.now().plusMonths(1);
    return new JPAQuery(em)
        .from(c)
        .where(
            (c.status.eq(StatusCliente.ATIVO).or(c.status.eq(StatusCliente.INATIVO)).and(c.id
                .notIn(new JPASubQuery().from(m).distinct().where(m.dataVencimento.gt(data))
                    .list(m.cliente.id))))).list(c);
  }
}
