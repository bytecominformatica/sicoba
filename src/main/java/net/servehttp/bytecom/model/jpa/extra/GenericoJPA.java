package net.servehttp.bytecom.model.jpa.extra;

import net.servehttp.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class GenericoJPA implements Serializable {

    private static final long serialVersionUID = -5183726686123081862L;
    @Inject
    protected EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public <T> T buscarPorId(Class<T> klass, int id) {
        return (T) em.find(klass, id);
    }

    public <T extends EntityGeneric> T salvar(T t) {
        if (t.getId() > 0) {
            em.merge(t);
            em.flush();
        } else {
            em.persist(t);
        }
        return t;
    }

    public <T> void remover(T t) {
        em.remove(em.merge(t));
    }

    /**
     * Busca até 200 registro filtrando por algum parametro.
     * <p>
     * <pre>
     *
     * @param campo - nome do campo que deseja verificar
     * @param valor - valor usado para verificação
     * @param klass - classe da entidade que deseja consultar
     * @return List
     * <p>
     * <pre>
     */
    public <T> List<T> buscarTodos(String campo, Object valor, Class<T> klass) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(klass);
        Root<T> entidade = q.from(klass);
        q.select(entidade).where(cb.equal(entidade.get(campo), valor));
        return em.createQuery(q).setMaxResults(200).getResultList();
    }

    public <T> T buscarUm(String campo, Object valor, Class<T> klass) {
        List<T> list = buscarTodos(campo, valor, klass);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }

    }

    public <T> List<T> buscarJpql(String jpql, Object valor, Class<T> klass) {
        return em.createQuery(jpql, klass).setParameter(1, valor).setMaxResults(200).getResultList();
    }

    public <T> List<T> buscarJpql(String jpql, Object valor1, Object valor2, Class<T> klass) {
        return em.createQuery(jpql, klass).setParameter(1, valor1).setParameter(2, valor2)
                .setMaxResults(200).getResultList();
    }

    /**
     * Busca até 200 registros.
     * <p>
     * <pre>
     *
     * @param klass - Classe da entidade que deseja buscar
     * @return List<T>
     * <p>
     * <pre>
     */
    public <T> List<T> buscarTodos(Class<T> klass) {
        return buscarTodos(klass, 200);
    }

    public <T> List<T> buscarTodos(Class<T> klass, int limit) {
        CriteriaQuery<T> q = em.getCriteriaBuilder().createQuery(klass);
        q.select(q.from(klass));
        return em.createQuery(q).setMaxResults(limit).getResultList();

    }

    public <T> List<T> buscarTodos(Class<T> klass, boolean ascendente, String campo, int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(klass);
        Root<T> c = q.from(klass);
        q.select(c);
        if (ascendente) {
            q.orderBy(cb.asc(c.get(campo)));
        } else {
            q.orderBy(cb.desc(c.get(campo)));
        }
        return em.createQuery(q).setMaxResults(limit).getResultList();

    }

}
