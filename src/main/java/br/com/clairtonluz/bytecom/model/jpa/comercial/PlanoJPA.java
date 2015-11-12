package br.com.clairtonluz.bytecom.model.jpa.comercial;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.QContrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.QPlano;
import com.mysema.query.jpa.impl.JPAQuery;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class PlanoJPA extends CrudJPA {

    private static final long serialVersionUID = 1857140370479772238L;
    private QPlano p = QPlano.plano;

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public Plano buscarPorId(int id) {
        return entityManager.find(Plano.class, id);
    }

    public List<Plano> buscarTodos() {
        return new JPAQuery(entityManager).from(p).orderBy(p.nome.asc()).list(p);
    }

    public Plano buscarPorNome(String nome) {
        return new JPAQuery(entityManager).from(p).where(p.nome.eq(nome)).uniqueResult(p);
    }

    public boolean isNotUsed(Plano plano) {
        QContrato contrato = QContrato.contrato;
        boolean isNotUsed = true;
        if(plano != null){
            List<Plano> planos = new JPAQuery(entityManager).from(contrato)
                    .where(contrato.plano.id.eq(plano.getId())).list(contrato.plano);
            isNotUsed = planos.isEmpty();
        }
        return isNotUsed;
    }
}
