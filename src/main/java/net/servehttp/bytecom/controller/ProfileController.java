package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Usuario;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.ProducesUtil;

/**
 * 
 * @author felipe
 *
 */
@Named
@ViewScoped
public class ProfileController implements Serializable {

  private static final long serialVersionUID = -2081234112300283530L;
  private Usuario usuario;

  @Inject
  private GenericoJPA genericoJPA;
  
  private ProducesUtil producesUtil = new ProducesUtil();

  public ProfileController() {}

  @PostConstruct
  public void load(){
	  usuario = producesUtil.getUsuarioLogado();
  }

  private boolean valida(Usuario usuario) {
    boolean result = true;
    List<Usuario> usuarios = genericoJPA.buscarTodos("login", usuario.getLogin(), Usuario.class);
    if (!usuarios.isEmpty() && usuarios.get(0).getId() != usuario.getId()) {
      AlertaUtil.alerta("Login já cadastrado!", AlertaUtil.ERROR);
      result = false;
    }
    usuarios = genericoJPA.buscarTodos("email", usuario.getEmail(), Usuario.class);
    if(!usuarios.isEmpty() && usuarios.get(0).getId() != usuario.getId()){
      AlertaUtil.alerta("Email já cadastrado!", AlertaUtil.ERROR);
      result = false;
    }
    return result;
  }

  public String salvar() {
    String page = null;
    if (valida(usuario)) {
      if (usuario.getId() == 0) {
        genericoJPA.salvar(usuario);
        AlertaUtil.alerta("Usuário gravado com sucesso!");
      } else {
        genericoJPA.atualizar(usuario);
        AlertaUtil.alerta("Usuário atualizado com sucesso!");
      }
      page = "list";
    }

    return page;
  }


  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

}
