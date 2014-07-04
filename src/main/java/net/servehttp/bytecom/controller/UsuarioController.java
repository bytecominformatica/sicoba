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

/**
 * 
 * @author felipe
 *
 */
@Named
@ViewScoped
public class UsuarioController implements Serializable {

  private static final long serialVersionUID = -2081234112300283530L;
  private List<Usuario> listUsuario;
  private Usuario usuario = new Usuario();
  private String page;

  @Inject
  private GenericoJPA genericoJPA;
  @Inject
  private Util util;

  public UsuarioController() {}

  @PostConstruct
  public void load() {
    listUsuario = genericoJPA.buscarTodos(Usuario.class);
    getParameters();
  }

  private boolean valida(Usuario usuario) {
    boolean result = true;
    List<Usuario> usuarios = genericoJPA.buscarTodos("login", usuario.getLogin(), Usuario.class);
    if (!usuarios.isEmpty() && usuarios.get(0).getId() != usuario.getId()) {
      AlertaUtil.alerta("Login já cadastrado!", AlertaUtil.ERROR);
      result = false;
    }
    return result;
  }

  public String salvar() {
    page = null;
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

  private void getParameters() {
    String usuarioId = util.getParameters("id");
    if (usuarioId != null && !usuarioId.isEmpty()) {
      usuario = genericoJPA.buscarPorId(Usuario.class, Integer.parseInt(usuarioId));
    }
  }

  public String remover() {
    page = null;
    genericoJPA.remover(usuario);
    page = "list";
    return page;
  }

  public List<Usuario> getListUsuario() {
    return listUsuario;
  }

  public void setListUsuario(List<Usuario> listUsuario) {
    this.listUsuario = listUsuario;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }



}
