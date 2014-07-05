package net.servehttp.bytecom;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.UsuarioJPA;
import net.servehttp.bytecom.persistence.entity.Usuario;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.WebUtil;

/**
 * 
 * @author clairton
 */
@Named
@RequestScoped
public class Authentication {

	private String usuario;
	private String senha;
	@Inject
	UsuarioJPA usuarioJPA;
	@Inject
	WebUtil webUtil;

	public void login() {

		Usuario l = usuarioJPA.buscaUsuario(usuario, senha);
		if (l != null) {
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			ec.getSessionMap().put("currentUser", l);
			webUtil.redirect("index.xhtml");
		} else {
			// final String assunto = "Tentativa de acesso";
			// final String para = "clairton.c.l@gmail.com";
			// final String ipQueTentouAcessar = NetworkUtil.INSTANCE.getIp();
			// final String mensagem =
			// "O seguinte ip tentou acessar o sistema: " + ipQueTentouAcessar
			// + " com o seguite usuario: " + usuario;
			//
			// MailUtil.INSTANCE.enviarDepois(assunto, mensagem, para);

			Logger.getLogger("USUÁRIO = " + usuario).severe("ACESSO NEGADO");
			AlertaUtil.alerta("Usuário e/ou senha inválida!", AlertaUtil.ERROR);
		}
	}

	public void logout() {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.getSessionMap().remove("currentUser");

		webUtil.redirect("login.xhtml");
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
