package net.servehttp.bytecom.percistence;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.AcessoJPA;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author clairton
 */
public class AcessoJPATest extends Assert {

	private static EntityManager em;
	private static final AcessoJPA acessoJPA = new AcessoJPA();

	@BeforeClass
	public static void setUp() {
		em = CreateEntityManager.INSTANCE.getEntityManager();
		acessoJPA.setEntityManager(em);
	}

	@Test
	public void deveriaBuscarUmPlanoPorNomeEBuscarUmPlanoPorId() {
		System.out.println(acessoJPA.getIpLivre());
	}

	@AfterClass
	public static void cleanUp() {
		em.close();
	}
}
