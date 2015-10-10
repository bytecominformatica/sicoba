package net.servehttp.bytecom.persistence.jpa;

import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
 */
@Transactional
public abstract class CrudJPA implements Serializable {

    private static final long serialVersionUID = -5750421956273634462L;

    protected abstract EntityManager getEm();

    public void remove(EntityGeneric entityGeneric) {
        getEm().remove(entityGeneric);
    }

    public EntityGeneric save(EntityGeneric entityGeneric) {
        if (entityGeneric.getId() > 0) {
            entityGeneric = getEm().merge(entityGeneric);
        } else {
            getEm().persist(entityGeneric);
        }
        return entityGeneric;
    }
}
