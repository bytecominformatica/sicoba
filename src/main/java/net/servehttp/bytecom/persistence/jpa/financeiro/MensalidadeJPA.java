package net.servehttp.bytecom.persistence.jpa.financeiro;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Cedente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.QCedente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.QMensalidade;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
 */
@Transactional
public class MensalidadeJPA implements Serializable {

    private static final long serialVersionUID = -5750421956273634462L;
    @Inject
    protected EntityManager em;
    private QMensalidade m = QMensalidade.mensalidade;

    public void remover(Mensalidade mensalidade) {
        new JPADeleteClause(em, m).where(m.id.eq(mensalidade.getId())).execute();
    }

    public void removerPorBoleto(int inicio, int fim) {
        new JPADeleteClause(em, m).where(m.numeroBoleto.between(inicio, fim)).execute();
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public List<Mensalidade> buscarMensalidadesPorBoletos(int numeroBoletoInicio, int numeroBoletoFim) {
        return new JPAQuery(em).from(m)
                .where(m.numeroBoleto.between(numeroBoletoInicio, numeroBoletoFim)).list(m);
    }

    public Cedente buscarCedente() {
        QCedente c = QCedente.cedente;
        return new JPAQuery(em).from(c).uniqueResult(c);
    }

    public Mensalidade buscarPorModalidadeNumeroBoleto(Integer modalidade, Integer numeroBoleto) {
        return new JPAQuery(em).from(m).where(m.numeroBoleto.eq(numeroBoleto).and(m.modalidade.eq(modalidade))).uniqueResult(m);
    }
}
