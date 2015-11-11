package br.com.clairtonluz.bytecom.model.jpa.comercial;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author clairton
 */
@Transactional
public class ContratoJPA extends CrudJPA {

    private static final long serialVersionUID = -2556507568580609030L;

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }


}
