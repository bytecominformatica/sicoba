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

	public <T> Object buscarPorId(Class<T> klass, int id) {
		return (T) em.find(klass, id);
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

	public <T> List<T> buscaTodos(Class<T> klass) {
		String className = klass.getName().substring(
				klass.getName().lastIndexOf('.') + 1);
		String jpql = "select t from " + className + " t";
		System.out.println(jpql);
		return em.createQuery(jpql, klass).getResultList();
	}

}
