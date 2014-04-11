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

    public Plano buscarPorId(int id) {
        return em.find(Plano.class, id);
    }

    public void salvar(Plano plano) {
        em.persist(plano);
    }

    public void atualizar(Plano plano) {
        em.merge(plano);
    }

    public void remover(Plano plano) {
        em.remove(em.merge(plano));
    }

    public List<Plano> buscaTodosOsPlanos() {
        return em.createQuery("select p from Plano p order by p.nome", Plano.class).getResultList();
    }

    public List<Plano> buscarPlanoPorNome(String pesquisa) {
        return em.createQuery("select p from Plano p where p.nome like :nome", Plano.class)
                .setParameter("nome", pesquisa + "%").getResultList();
    }
}
