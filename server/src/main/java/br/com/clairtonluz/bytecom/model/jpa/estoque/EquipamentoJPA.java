package br.com.clairtonluz.bytecom.model.jpa.estoque;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.QContrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.QEquipamento;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.StatusEquipamento;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.TipoEquipamento;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class EquipamentoJPA extends CrudJPA {

    private static final long serialVersionUID = 7528131197866761853L;
    private QEquipamento e = QEquipamento.equipamento;
    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public List<Equipamento> findAll() {
        return new JPAQuery(entityManager).from(e).limit(200).orderBy(e.id.desc()).list(e);
    }

    public List<Equipamento> buscarNaoUtilizados(TipoEquipamento tipo,
                                                 StatusEquipamento status) {
        BooleanExpression condicao;
        QContrato contrato = QContrato.contrato;
        if (tipo == TipoEquipamento.INSTALACAO) {
            condicao = e.tipo.eq(tipo).and(e.status.eq(status).and(
                    e.id.notIn(new JPASubQuery().from(contrato).where(contrato.equipamento.id.isNotNull()).list(contrato.equipamento.id))
            ));
        } else {
            condicao = e.tipo.eq(tipo).and(e.status.eq(status).and(
                    e.id.notIn(new JPASubQuery().from(contrato).where(contrato.equipamentoWifi.id.isNotNull()).list(contrato.equipamento.id))
            ));
        }

        return new JPAQuery(entityManager).from(e).where(condicao).orderBy(e.modelo.asc()).list(e);
    }

    public Equipamento buscarPorId(int id) {
        return new JPAQuery(entityManager).from(e).where(e.id.eq(id)).uniqueResult(e);
    }

    public Equipamento buscarPorMac(String mac) {
        return new JPAQuery(entityManager).from(e).where(e.mac.eq(mac)).uniqueResult(e);
    }

}
