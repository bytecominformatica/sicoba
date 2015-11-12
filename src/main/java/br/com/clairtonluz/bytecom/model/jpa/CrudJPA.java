package br.com.clairtonluz.bytecom.model.jpa;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by clairtonluz on 04/11/15.
 */
@Transactional
public abstract class CrudJPA implements Serializable {

    private static final long serialVersionUID = -5750421956273634462L;

    public EntityGeneric save(EntityGeneric entityGeneric) {
        if (entityGeneric.getId() > 0) {
            entityGeneric = getEntityManager().merge(entityGeneric);
        } else {
            getEntityManager().persist(entityGeneric);
        }
        return entityGeneric;
    }

    public void save(List<EntityGeneric> list) {
        list.forEach(e -> {
            save(e);
        });
    }

    public void remove(EntityGeneric entityGeneric) {
        getEntityManager().remove(getEntityManager().merge(entityGeneric));
    }

    public abstract EntityManager getEntityManager();

    public abstract void setEntityManager(EntityManager entityManager);
}
