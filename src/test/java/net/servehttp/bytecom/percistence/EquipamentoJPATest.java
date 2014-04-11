package net.servehttp.bytecom.percistence;

import java.util.List;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.EquipamentoJPA;
import net.servehttp.bytecom.persistence.entity.Equipamento;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EquipamentoJPATest {

	private static EntityManager em;
	private static final EquipamentoJPA equipamentoJPA = new EquipamentoJPA();
	private static final String teste = "Teste360";
	private static final String mac = "01:03:04:22:22:11";

	@BeforeClass
	public static void setUp() {
		em = CreateEntityManager.INSTANCE.getEntityManager();
		equipamentoJPA.setEntityManager(em);
		em.getTransaction().begin();
		Equipamento e = new Equipamento();
		e.setDescricao(teste);
		e.setMac(mac);
		e.setMarca(teste);
		e.setModelo(teste);
		e.setStatus(Equipamento.STATUS_OK);
		e.setTipo(Equipamento.TIPO_INSTALACAO);
		equipamentoJPA.salvar(e);
	}

	@Test
	public void deveriaAtualizarUmEquipamento() {
		Equipamento e = equipamentoJPA.buscarEquipamentoPorMac(mac);
		Assert.assertNotNull(e);

		Assert.assertEquals(teste, e.getDescricao());

		String testeAtualizar = "TestandoAtualizar";
		e.setDescricao(testeAtualizar);
		equipamentoJPA.atualizar(e);

		Equipamento e2 = equipamentoJPA.buscarEquipamentoPorMac(mac);
		Assert.assertEquals(testeAtualizar, e2.getDescricao());

		e2.setDescricao(teste);
		equipamentoJPA.atualizar(e2);
	}

	@Test
	public void deveriaBuscarTodosEquipamentosEDeveriaBuscarUmEquipamentoPeloId() {
		List<Equipamento> list = equipamentoJPA.buscaTodosOsEquipamentos();
		Assert.assertTrue(list.size() > 0);

		int id = list.get(0).getId();
		Equipamento e = equipamentoJPA.buscarPorId(id);
		Assert.assertNotNull(e);
		Assert.assertTrue(id == e.getId());
	}

	@Test
	public void deveriaBuscarOsEquipamentosNaoFiltrandoPorTipo() {
		List<Equipamento> list = equipamentoJPA.buscaEquipamentosNaoUtilizados(Equipamento.TIPO_INSTALACAO, Equipamento.STATUS_OK);
		Assert.assertNotNull(list);

		list = equipamentoJPA.buscaEquipamentosNaoUtilizados(Equipamento.TIPO_WIFI, Equipamento.STATUS_OK);
		Assert.assertNotNull(list);
	}

	@Test
	public void macDeveriaExitir() {
		Assert.assertTrue(equipamentoJPA.existMAC(mac));
	}

	@Test
	public void macNaoDeveriaExitir() {
		Assert.assertFalse(equipamentoJPA.existMAC("00:11:22:33:44:55"));
	}

	@AfterClass
	public static void cleanUp() {
		Equipamento e = equipamentoJPA.buscarEquipamentoPorMac(mac);
		Assert.assertEquals(mac, e.getMac());
		equipamentoJPA.remover(e);
		e = equipamentoJPA.buscarEquipamentoPorMac(mac);
		Assert.assertNull(e);
		em.getTransaction().commit();
		em.close();
	}

}
