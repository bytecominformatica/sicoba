/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.servehttp.bytecom.persistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Usuario;

/**
 * 
 * @author clairton
 */
@Transactional
public class UsuarioJPA {

	@PersistenceContext(unitName = "bytecom-pu")
	EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * Busca usuário por login e senha
	 * 
	 * @param login
	 * @param senha
	 * @return Usuario
	 */
	public Usuario buscaUsuario(String login, String senha) {
		Usuario l = null;
		try {
			l = em.createQuery(
					"SELECT u FROM Usuario u where u.login = :login and u.senha = FUNCTION('sha2', :senha , 256)",
					Usuario.class).setParameter("login", login)
					.setParameter("senha", senha).getSingleResult();
		} catch (NoResultException e) {
		}

		return l;
	}

}
