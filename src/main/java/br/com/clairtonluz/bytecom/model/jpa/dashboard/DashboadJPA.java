package br.com.clairtonluz.bytecom.model.jpa.dashboard;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.QCliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.QContrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.QMensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class DashboadJPA implements Serializable {

    private static final long serialVersionUID = 4057406973170798760L;
    @Inject
    protected EntityManager em;
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
        LocalDate data = LocalDate.now().plusMonths(2);
        return new JPAQuery(em)
                .from(c)
                .where(
                        (c.status.eq(StatusCliente.ATIVO).or(c.status.eq(StatusCliente.INATIVO)).and(c.id
                                .notIn(new JPASubQuery().from(m).distinct().where(m.dataVencimento.gt(data))
                                        .orderBy(m.cliente.contrato.dataInstalacao.asc()).list(m.cliente.id)))))
                .list(c);
    }
}
