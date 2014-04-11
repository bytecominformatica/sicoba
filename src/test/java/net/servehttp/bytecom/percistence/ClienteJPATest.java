package net.servehttp.bytecom.percistence;

import java.util.List;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.ClienteJPA;
import net.servehttp.bytecom.persistence.entity.Cliente;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author clairton
 */
public class ClienteJPATest {

	private static EntityManager em;
	private static final ClienteJPA clienteJPA = new ClienteJPA();
	private static final String teste = "Teste360";
	private static final String email = "teste@teste.com";
	private static final String fone = "(85)8758-9380";

	@BeforeClass
	public static void setUp() {
		em = CreateEntityManager.INSTANCE.getEntityManager();
		clienteJPA.setEntityManager(em);
		em.getTransaction().begin();

		Cliente c = new Cliente();
		c.setNome(teste);
		c.setContato(teste);
		c.setCpf("023.039.345-05");
		c.setEmail(email);

		c.setEndereco(null);
		c.setFoneContato(teste);
		c.setRg(teste);
		c.setFoneTitular(fone);

		clienteJPA.salvar(c);
	}

	@Test
	public void bDeveriaAtualizarUmCliente() {
		List<Cliente> list = clienteJPA.buscarClientePorNome(teste);
		Assert.assertEquals(1, list.size());

		Cliente c1 = list.get(0);
		Assert.assertNotNull(c1);
		Assert.assertEquals(teste, c1.getNome());

		String testeAtualizar = "TestandoAtualizar";
		c1.setNome(testeAtualizar);
		clienteJPA.atualizar(c1);

		list = clienteJPA.buscarClientePorNome(testeAtualizar);
		Assert.assertEquals(1, list.size());
		Cliente c2 = list.get(0);
		Assert.assertEquals(c1.getNome(), c2.getNome());

		c2.setNome(teste);
		clienteJPA.atualizar(c2);
	}

	@Test
	public void deveriaBuscarUmClientePorNomeEBuscarUmClientePorId() {
		List<Cliente> list = clienteJPA.buscarClientePorNome(teste);
		Assert.assertEquals(1, list.size());

		int id = list.get(0).getId();
		Cliente c = clienteJPA.buscarPorId(id);
		Assert.assertNotNull(c);
		Assert.assertEquals(id, c.getId());
	}

	@Test
	public void deveriaBuscarUmClientePorRg() {
		Cliente c = clienteJPA.buscarClientePorRg(teste);
		Assert.assertNotNull(c);
		Assert.assertEquals(teste, c.getRg());
	}

	@Test
	public void deveriaBuscarUmClientePorCpf() {
		Cliente c = clienteJPA.buscarClientePorCpf("023.039.345-05");
		Assert.assertNotNull(c);
		Assert.assertEquals("023.039.345-05", c.getCpf());
	}

	@Test
	public void deveriaBuscarUmClientePorEmail() {
		Cliente c = clienteJPA.buscarClientePorEmail("teste@teste.com");
		Assert.assertNotNull(c);
		Assert.assertEquals("teste@teste.com", c.getEmail());
	}

	@Test
	public void deveriaBuscarTodosOsClientes() {
		List<Cliente> list = clienteJPA.buscaTodosOsClientes();
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void deveriaBuscarClientePorNomeFoneEmail() {
		List<Cliente> list = clienteJPA.buscaClientesPorNomeFoneEmail(teste, fone, email);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void deveriaBuscarClientePorNomeFoneEmailSemPassarEmail() {
		List<Cliente> list = clienteJPA.buscaClientesPorNomeFoneEmail(teste, fone, null);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void deveriaBuscarClientePorNomeFoneEmailSemPassarFone() {
		List<Cliente> list = clienteJPA.buscaClientesPorNomeFoneEmail(teste, null, email);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void deveriaBuscarClientePorNomeFoneEmailSemPassarNome() {
		List<Cliente> list = clienteJPA.buscaClientesPorNomeFoneEmail(null, fone, email);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void deveriaBuscarClientePorNomeFoneEmailSemPassarNomeEFone() {
		List<Cliente> list = clienteJPA.buscaClientesPorNomeFoneEmail(null, null, email);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void deveriaBuscarClientePorNomeFoneEmailSemPassarNomeEEmail() {
		List<Cliente> list = clienteJPA.buscaClientesPorNomeFoneEmail(null, fone, null);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void naoDeveriaBuscarClientePoisNaoPassouNomeFoneENemEmail() {
		List<Cliente> list = clienteJPA.buscaClientesPorNomeFoneEmail(null, null, null);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.isEmpty());
	}

	@Test
	public void deveriaBuscarUltimosClientesAlterados() {
		List<Cliente> list = clienteJPA.buscaUltimosClientesAlterados();
		Assert.assertNotNull(list);
	}

	@AfterClass
	public static void cleanUp() {
		List<Cliente> list = clienteJPA.buscarClientePorNome(teste);
		Assert.assertEquals(1, list.size());
		clienteJPA.remover(list.get(0));
		list = clienteJPA.buscarClientePorNome(teste);
		Assert.assertEquals(0, list.size());

		em.getTransaction().commit();
		em.close();
	}
}
