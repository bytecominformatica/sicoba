package br.com.clairtonluz.bytecom.model.jpa.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.*;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
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

    public List<Mensalidade> buscarMensalidadesPorBoletos(int modalidade, int numeroBoletoInicio, int numeroBoletoFim) {
        return new JPAQuery(entityManager).from(m)
                .where(m.numeroBoleto.between(numeroBoletoInicio, numeroBoletoFim).and(m.modalidade.eq(modalidade))).list(m);
    }

    public Mensalidade buscarPorModalidadeNumeroBoleto(int modalidade, int numeroBoleto) {
        return new JPAQuery(entityManager).from(m).where(m.numeroBoleto.eq(numeroBoleto).and(m.modalidade.eq(modalidade))).uniqueResult(m);
    }

    public Mensalidade buscarPorId(int id) {
        return new JPAQuery(entityManager).from(m).where(m.id.eq(id)).uniqueResult(m);
    }

    public List<Mensalidade> bucarPorCliente(Cliente cliente) {
        return new JPAQuery(entityManager).from(m).where(m.cliente.id.eq(cliente.getId())).list(m);
    }

    public List<Mensalidade> buscarMensaliadadesAtrasada() {
        return new JPAQuery(entityManager).from(m)
                .where(m.status.eq(StatusMensalidade.PENDENTE).and(m.dataVencimento.lt(new Date())))
                .orderBy(m.dataVencimento.asc()).list(m);
    }
}
