package br.com.clairtonluz.bytecom.model.jpa.dashboard;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.*;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.QMensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;

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


    public List<Contrato> buscarNovosContratos() {
        QContrato c = QContrato.contrato;
        return new JPAQuery(em).from(c).orderBy(c.dataInstalacao.desc()).limit(10).list(c);
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
}
