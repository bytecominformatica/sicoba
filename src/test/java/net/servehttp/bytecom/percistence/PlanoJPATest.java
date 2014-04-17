package net.servehttp.bytecom.percistence;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.PlanoJPA;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author clairton
 */
public class PlanoJPATest extends Assert {

	private static EntityManager em;
	private static final PlanoJPA planoJPA = new PlanoJPA();
	private static final String teste = "Teste360";

	@BeforeClass
	public static void setUp() {
		em = CreateEntityManager.INSTANCE.getEntityManager();
		planoJPA.setEntityManager(em);
	}

	@Test
	public void deveriaBuscarUmPlanoPorNomeEBuscarUmPlanoPorId() {
		planoJPA.buscarPlanoPorNome(teste);
	}

	@AfterClass
	public static void cleanUp() {
		em.close();
	}
}
