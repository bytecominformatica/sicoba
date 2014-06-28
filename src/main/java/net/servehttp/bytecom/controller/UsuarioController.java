package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable {

  private static final long serialVersionUID = -2081234112300283530L;
  private List<Usuario> listUsuario;
  private Usuario usuario = new Usuario();
  private String page;
  
  @Inject
  private GenericoJPA genericoJPA;
  
  public UsuarioController(){}
  
  @PostConstruct
  public void load(){
    listUsuario = genericoJPA.buscarTodos(Usuario.class);
  }
  
  public String salvar(){
   page = null;
   genericoJPA.salvar(usuario);
   return page;
  }

  
  public String remove(){
    page = null;
    genericoJPA.remover(usuario);
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
