package net.servehttp.bytecom.model.jpa;

import net.servehttp.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Created by clairtonluz on 04/11/15.
 */
public class CrudJPA implements Serializable {

    @Inject
    protected EntityManager entityManager;

    public EntityGeneric save(EntityGeneric entityGeneric) {
        if (entityGeneric.getId() > 0) {
            entityGeneric = entityManager.merge(entityGeneric);
        } else {
            entityManager.persist(entityGeneric);
        }
        return entityGeneric;
    }

    public void save(List<EntityGeneric> list) {
        entityManager.getTransaction().begin();
        list.forEach(e -> {
            if (e.getId() > 0) {
                entityManager.merge(e);
            } else {
                entityManager.persist(e);
            }
        });
        entityManager.getTransaction().commit();
    }

    public void remove(EntityGeneric entityGeneric) {
        entityManager.remove(entityGeneric);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
