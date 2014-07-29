package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 
 * @author Clairton Luz - clairton.c.l@gmail.com
 *
 */
@Named
@RequestScoped
public class SecurityController implements Serializable {

	private static final long serialVersionUID = -4657746545855537894L;
	private static final Logger LOGGER = Logger
			.getLogger(SecurityController.class.getSimpleName());

	private String username;
	private String password;
//	private Subject currentUser = SecurityUtils.getSubject();
	
	public void authenticate(){
//		if(!currentUser.isAuthenticated()){
//			System.out.println("não autenticado");
//		} else {
//			System.out.println("autenticado");
//			
//		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
