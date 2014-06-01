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
  }

  @Test
  public void deveriaBuscarUmClientePorNomeEBuscarUmClientePorId() {
    clienteJPA.buscarClientePorNome(teste);
  }

  @Test
  public void deveriaBuscarUmClientePorEmail() {
    clienteJPA.buscarClientePorEmail("teste@teste.com");
  }

  @Test
  public void deveriaBuscarClientePorNomeFoneEmail() {
    clienteJPA.buscaClientesPorNomeFoneEmail(teste, fone, email);
  }

  @Test
  public void deveriaBuscarClientePorNomeFoneEmailSemPassarEmail() {
    clienteJPA.buscaClientesPorNomeFoneEmail(teste, fone, null);
  }

  @Test
  public void deveriaBuscarClientePorNomeFoneEmailSemPassarFone() {
    clienteJPA.buscaClientesPorNomeFoneEmail(teste, null, email);
  }

  @Test
  public void deveriaBuscarClientePorNomeFoneEmailSemPassarNome() {
    clienteJPA.buscaClientesPorNomeFoneEmail(null, fone, email);
  }

  @Test
  public void deveriaBuscarClientePorNomeFoneEmailSemPassarNomeEFone() {
    clienteJPA.buscaClientesPorNomeFoneEmail(null, null, email);
  }

  @Test
  public void deveriaBuscarClientePorNomeFoneEmailSemPassarNomeEEmail() {
    clienteJPA.buscaClientesPorNomeFoneEmail(null, fone, null);
  }

  @Test
  public void naoDeveriaBuscarClientePoisNaoPassouNomeFoneENemEmail() {
    clienteJPA.buscaClientesPorNomeFoneEmail(null, null, null);
  }

  @Test
  public void deveriaBuscarUltimosClientesAlterados() {
    List<Cliente> list = clienteJPA.buscaUltimosClientesAlterados();
    Assert.assertNotNull(list);
  }

  @AfterClass
  public static void cleanUp() {
    em.close();
  }
}
