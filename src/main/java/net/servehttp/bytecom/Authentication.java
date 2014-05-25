package net.servehttp.bytecom;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.UsuarioJPA;
import net.servehttp.bytecom.persistence.entity.Usuario;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@ManagedBean
public class Authentication {

	private String usuario;
	private String senha;
	@Inject
	UsuarioJPA usuarioJPA;

	public String login() {
		String page = "login.xhtml";

		Usuario l = usuarioJPA.buscaUsuario(usuario, senha);
		if (l != null) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getSessionMap().put("currentUser", l);
			page = "index.xhtml";
		} else {
			// final String assunto = "Tentativa de acesso";
			// final String para = "clairton.c.l@gmail.com";
			// final String ipQueTentouAcessar = NetworkUtil.INSTANCE.getIp();
			// final String mensagem =
			// "O seguinte ip tentou acessar o sistema: " + ipQueTentouAcessar
			// + " com o seguite usuario: " + usuario;
			//
			// MailUtil.INSTANCE.enviarDepois(assunto, mensagem, para);

			AlertaUtil.alerta("Usuário e/ou senha inválida!", AlertaUtil.ERROR);
		}
		return page;
	}

	public String logout() {
		String page = "/bytecom/login.xhtml";
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.getSessionMap().remove("currentUser");

		return page;
	}

	public Usuario getUsuarioLogado() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		return (Usuario) ec.getSessionMap().get("currentUser");
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
