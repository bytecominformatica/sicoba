package net.servehttp.bytecom.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * 
 * @author clairton
 * @param <T>
 */
@Transactional
public class GenericoJPA {

	@PersistenceContext(unitName = "bytecom-pu")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public <T> T buscarPorId(Class<T> klass, int id) {
		return ((T) em.find(klass, id));
	}

	public <T> void salvar(T t) {
		em.persist(t);
	}

	public <T> void atualizar(T t) {
		em.merge(t);
	}

	public <T> void remover(T t) {
		em.remove(em.merge(t));
	}

	public <T> List<T> buscarTodos(String hql, String parametro, Class<T> klass) {
		return em.createQuery(hql, klass).setParameter(1, parametro)
				.getResultList();
	}

	public <T> List<T> buscarTodos(String hql, int parametro, Class<T> klass) {
		return em.createQuery(hql, klass).setParameter(1, parametro)
				.getResultList();
	}

	public <T> List<T> buscarTodos(Class<T> klass) {
		String className = klass.getName().substring(
				klass.getName().lastIndexOf('.') + 1);

		String jpql = "select t from " + className + " t";
		return em.createQuery(jpql, klass).getResultList();
	}

}
