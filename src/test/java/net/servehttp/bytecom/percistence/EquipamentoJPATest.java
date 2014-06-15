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
	
	@BeforeClass
	public static void setUp() {
		em = CreateEntityManager.INSTANCE.getEntityManager();
		equipamentoJPA.setEntityManager(em);
	}

	@Test
	public void deveriaBuscarOsEquipamentosNaoFiltrandoPorTipo() {
		List<Equipamento> list = equipamentoJPA.buscaEquipamentosNaoUtilizados(Equipamento.TIPO_INSTALACAO, Equipamento.STATUS_OK);
		Assert.assertNotNull(list);

		list = equipamentoJPA.buscaEquipamentosNaoUtilizados(Equipamento.TIPO_WIFI, Equipamento.STATUS_OK);
		Assert.assertNotNull(list);
	}

	@AfterClass
	public static void cleanUp() {
		em.close();
	}

}
