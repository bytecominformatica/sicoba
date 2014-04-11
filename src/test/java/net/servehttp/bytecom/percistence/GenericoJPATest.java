package net.servehttp.bytecom.percistence;

import java.util.List;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Plano;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GenericoJPATest {
	private static EntityManager em;
	private static GenericoJPA genericoJPA;

	@BeforeClass
	public static void setUp() {
		em = CreateEntityManager.INSTANCE.getEntityManager();
		genericoJPA = new GenericoJPA();
		genericoJPA.setEntityManager(em);
	}

	@Test
	public void deveriaDeveriaSalva() {
		List<Plano> list = genericoJPA.buscaTodos(Plano.class);
		Assert.assertNotNull(list);
	}

	@AfterClass
	public static void closeUp() {
		em.close();
	}

}
