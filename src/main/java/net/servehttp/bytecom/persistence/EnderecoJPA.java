package net.servehttp.bytecom.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Bairro;
import net.servehttp.bytecom.persistence.entity.Cidade;

/**
 * 
 * @author clairton
 */
@Transactional
public class EnderecoJPA {

	@PersistenceContext(unitName = "bytecom-pu")
	private EntityManager em;

	public List<Cidade> buscaTodasCidades() {
		return em.createQuery("select c from Cidade c", Cidade.class)
				.getResultList();
	}

	public Bairro buscaBairroPorId(int bairroId) {
		return em.createQuery("select b from Bairro b where b.id = :bairroId", Bairro.class)
				.setParameter("bairroId", bairroId)
				.getSingleResult();
	}

	public Cidade buscaCidadePorId(int id) {
		return em.createQuery("select c from Cidade c where cb.id = :id", Cidade.class)
				.setParameter("bairroId", id)
				.getSingleResult();
	}

}