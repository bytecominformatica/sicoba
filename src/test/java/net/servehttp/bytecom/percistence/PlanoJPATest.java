package net.servehttp.bytecom.percistence;

import java.util.List;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.PlanoJPA;
import net.servehttp.bytecom.persistence.entity.Plano;

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
		em.getTransaction().begin();
		Plano p = new Plano();
		p.setNome(teste);
		p.setWifi(true);
		p.setDownload(1000);
		p.setUpload(1000);
		p.setValorInstalacao(192);
		p.setValorMensalidade(35);
		planoJPA.salvar(p);
	}

	@Test
	public void deveriaAtualizarUmPlano() {
		List<Plano> list = planoJPA.buscarPlanoPorNome(teste);
		assertEquals(1, list.size());

		Plano p1 = list.get(0);
		assertNotNull(p1);
		assertEquals(teste, p1.getNome());

		String testeAtualizar = "TestandoAtualizar";
		p1.setNome(testeAtualizar);
		planoJPA.atualizar(p1);

		list = planoJPA.buscarPlanoPorNome(testeAtualizar);
		assertEquals(1, list.size());
		Plano p2 = list.get(0);
		assertEquals(testeAtualizar, p2.getNome());

		p2.setNome(teste);
		planoJPA.atualizar(p2);
	}

	@Test
	public void deveriaBuscarUmPlanoPorNomeEBuscarUmPlanoPorId() {
		List<Plano> list = planoJPA.buscarPlanoPorNome(teste);
		assertEquals(1, list.size());

		int id = list.get(0).getId();
		Plano p = planoJPA.buscarPorId(id);
		assertNotNull(p);
		assertEquals(id, p.getId());
	}

	@Test
	public void deveriaBuscarTodosOsPlanos() {
		List<Plano> list = planoJPA.buscaTodosOsPlanos();
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}

	@AfterClass
	public static void cleanUp() {
		List<Plano> list = planoJPA.buscarPlanoPorNome(teste);
		assertEquals(1, list.size());
		planoJPA.remover(list.get(0));
		list = planoJPA.buscarPlanoPorNome(teste);
		assertEquals(0, list.size());
		em.getTransaction().commit();
		em.close();
	}
}
