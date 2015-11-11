package br.com.clairtonluz.bytecom.util.produces.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(unitName = "bytecom-pu")
    private EntityManager em;

    @Produces
    @RequestScoped
    public EntityManager create() {
        return em;
    }

}
