package br.com.clairtonluz.bytecom.percistence;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.clairtonluz.bytecom.facede.CreateEntityManager;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.StatusEquipamento;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.TipoEquipamento;
import br.com.clairtonluz.bytecom.model.jpa.estoque.EquipamentoJPA;

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
		List<Equipamento> list = equipamentoJPA.buscarNaoUtilizados(TipoEquipamento.INSTALACAO, StatusEquipamento.OK);
		Assert.assertNotNull(list);

		list = equipamentoJPA.buscarNaoUtilizados(TipoEquipamento.WIFI, StatusEquipamento.OK);
		Assert.assertNotNull(list);
	}

	@AfterClass
	public static void cleanUp() {
		em.close();
	}

}
