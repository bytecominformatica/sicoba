package net.servehttp.bytecom.percistence;

import java.util.List;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.estoque.jpa.EquipamentoJPA;
import net.servehttp.bytecom.estoque.jpa.entity.Equipamento;
import net.servehttp.bytecom.estoque.jpa.entity.StatusEquipamento;
import net.servehttp.bytecom.estoque.jpa.entity.TipoEquipamento;
import net.servehttp.bytecom.facede.CreateEntityManager;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EquipamentoJPATest {

	private static EntityManager em;
	private static final EquipamentoJPA equipamentoJPA = new EquipamentoJPA();
	
	@BeforeClass
	public static void setUp() {
		em = CreateEntityManager.INSTANCE.getEntityManager();
		equipamentoJPA.setEntityManager(em);
	}

	@Test
	public void deveriaBuscarOsEquipamentosNaoFiltrandoPorTipo() {
		List<Equipamento> list = equipamentoJPA.buscarEquipamentosNaoUtilizados(TipoEquipamento.INSTALACAO, StatusEquipamento.OK);
		Assert.assertNotNull(list);

		list = equipamentoJPA.buscarEquipamentosNaoUtilizados(TipoEquipamento.WIFI, StatusEquipamento.OK);
		Assert.assertNotNull(list);
	}

	@AfterClass
	public static void cleanUp() {
		em.close();
	}

}
