package br.com.clairtonluz.bytecom.facede;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public enum CreateEntityManager {
	INSTANCE;
	private EntityManager em;

	public EntityManager getEntityManager() {
		if (em == null || !em.isOpen()) {
			em = Persistence.createEntityManagerFactory("teste-pu")
					.createEntityManager();
		}
		return em;
	}
}
