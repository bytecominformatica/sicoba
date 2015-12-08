package br.com.clairtonluz.bytecom.util.produces.jpa;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

    @PersistenceContext(unitName = "bytecom-pu")
    private EntityManager em;

    @Produces
    public EntityManager create() {
        return em;
    }
//
//    public void close(@Disposes EntityManager em) {
//        if (em.isOpen()) {
//            em.close();
//        }
//    }

}
