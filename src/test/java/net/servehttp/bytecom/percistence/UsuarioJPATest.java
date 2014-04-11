package net.servehttp.bytecom.percistence;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.UsuarioJPA;
import net.servehttp.bytecom.persistence.entity.Usuario;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UsuarioJPATest {
	private EntityManager em;
	private UsuarioJPA usuarioJPA;

	@Before
	public void setUp() {
		em = CreateEntityManager.INSTANCE.getEntityManager();
		usuarioJPA = new UsuarioJPA();
		usuarioJPA.setEntityManager(em);
	}
	
	@Test
	public void deveriaBuscarUsuarioPorLoginESenha(){
		Usuario u = usuarioJPA.buscaUsuario("logindeteste", "logindeteste");
		Assert.assertNotNull(u);
	}

	@Test
	public void deveriaRecusarBuscarDeUsuarioPorSenhaIncorreta(){
		Usuario u = usuarioJPA.buscaUsuario("logindeteste", "senhaIncorreta");
		Assert.assertNull(u);
	}

	@Test
	public void deveriaRecusarBuscarDeUsuarioPorUsuarioIncorreta(){
		Usuario u = usuarioJPA.buscaUsuario("usuarioIncorreto", "logindeteste");
		Assert.assertNull(u);
	}
	
	@After
	public void closeUp(){
		em.close();
	}

}
