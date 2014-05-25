package net.servehttp.bytecom.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author Clairton Luz
 *
 */
@Named
@RequestScoped
public class HtmlController {

	private String email;
	public void exibir(){
		System.out.println("Exivindo com sucesso = " + email);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
