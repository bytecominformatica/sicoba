package net.servehttp.bytecom.dashboard.jpa;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.comercial.jpa.entity.QCliente;
import net.servehttp.bytecom.comercial.jpa.entity.QContrato;
import net.servehttp.bytecom.comercial.jpa.entity.StatusCliente;
import net.servehttp.bytecom.extra.jpa.GenericoJPA;
import net.servehttp.bytecom.financeiro.jpa.entity.Mensalidade;
import net.servehttp.bytecom.financeiro.jpa.entity.QMensalidade;
import net.servehttp.bytecom.financeiro.jpa.entity.StatusMensalidade;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author clairton
 */
@Transactional
public class DashboadJPA extends GenericoJPA {

  private static final long serialVersionUID = 4057406973170798760L;
  private LocalDate from;
  private LocalDate to;

  public DashboadJPA() {
    from = LocalDate.now().withDayOfMonth(1);
    to = LocalDate.now();
    to = to.withDayOfMonth(to.lengthOfMonth());
        
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
    System.out.println("AA " + d);
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
