package br.com.clairtonluz.bytecom.model.jpa.comercial;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.QContrato;
import com.mysema.query.jpa.impl.JPAQuery;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class ContratoJPA extends CrudJPA {

    private static final long serialVersionUID = -2556507568580609030L;

    @Inject
    private EntityManager entityManager;

    public ContratoJPA() {
    }

    public ContratoJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public List<Contrato> buscarTodosPorDataInstalacao(LocalDate from, LocalDate to) {
        QContrato c = QContrato.contrato;
        return new JPAQuery(entityManager).from(c).where(c.dataInstalacao.between(from, to)).list(c);
    }

    public Contrato buscarPorId(Integer id) {
        return entityManager.find(Contrato.class, id);
    }
}
