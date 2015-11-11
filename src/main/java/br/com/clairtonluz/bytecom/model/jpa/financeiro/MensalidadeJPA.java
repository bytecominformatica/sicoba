package br.com.clairtonluz.bytecom.model.jpa.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Cedente;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.QCedente;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.QMensalidade;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class MensalidadeJPA extends CrudJPA {

    private static final long serialVersionUID = -5750421956273634462L;

    private QMensalidade m = QMensalidade.mensalidade;
    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public void remover(Mensalidade mensalidade) {
        new JPADeleteClause(entityManager, m).where(m.id.eq(mensalidade.getId())).execute();
    }

    public void removerPorBoleto(int inicio, int fim) {
        new JPADeleteClause(entityManager, m).where(m.numeroBoleto.between(inicio, fim)).execute();
    }

    public List<Mensalidade> buscarMensalidadesPorBoletos(int modalidade, int numeroBoletoInicio, int numeroBoletoFim) {
        return new JPAQuery(entityManager).from(m)
                .where(m.numeroBoleto.between(numeroBoletoInicio, numeroBoletoFim).and(m.modalidade.eq(modalidade))).list(m);
    }

    public Cedente buscarCedente() {
        QCedente c = QCedente.cedente;
        return new JPAQuery(entityManager).from(c).uniqueResult(c);
    }

    public Mensalidade buscarPorModalidadeNumeroBoleto(int modalidade, int numeroBoleto) {
        return new JPAQuery(entityManager).from(m).where(m.numeroBoleto.eq(numeroBoleto).and(m.modalidade.eq(modalidade))).uniqueResult(m);
    }

    public Mensalidade buscarPorId(int id) {
        return new JPAQuery(entityManager).from(m).where(m.id.eq(id)).uniqueResult(m);
    }

}
