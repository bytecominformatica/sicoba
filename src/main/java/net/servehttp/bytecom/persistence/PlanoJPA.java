package net.servehttp.bytecom.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Plano;

/**
 *
 * @author clairton
 */
@Transactional
public class PlanoJPA {

    @PersistenceContext(unitName = "bytecom-pu")
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public List<Plano> buscarPlanoPorNome(String pesquisa) {
        return em.createQuery("select p from Plano p where p.nome like :nome", Plano.class)
                .setParameter("nome", pesquisa + "%").getResultList();
    }
}
