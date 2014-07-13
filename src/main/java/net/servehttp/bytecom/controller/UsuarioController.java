package net.servehttp.bytecom.controller;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import net.servehttp.bytecom.ejb.ProfileImageEJB;
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
  private String image;
  private static final String EXTENSION = "png";
  
  private Part file;

  @Inject
  private GenericoJPA genericoJPA;
  @Inject
  private Util util;
  @Inject
  private ProfileImageEJB profileImage;

  public UsuarioController() {}

  @PostConstruct
  public void load() {
    listUsuario = genericoJPA.buscarTodos(Usuario.class);
    getParameters();
    if(usuario.getImg() != null){
      exibirImagem();
    }else{
      setImage("/resources/img/default_avatar.png");
    }
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
    page = null;
    if (valida(usuario)) {
      if (usuario.getId() == 0) {
        usuario.setImg(profileImage.setThumbnail(profileImage.tratarImagem(file), EXTENSION));
        genericoJPA.salvar(usuario);
        AlertaUtil.alerta("Usuário gravado com sucesso!");
      } else {
        usuario.setImg(profileImage.setThumbnail(profileImage.tratarImagem(file), EXTENSION));
        genericoJPA.atualizar(usuario);
        AlertaUtil.alerta("Usuário atualizado com sucesso!");
      }
      page = "list";
    }

    return page;
  }
  
  public void exibirImagem(){
    try{
      FacesContext context = FacesContext.getCurrentInstance();
      ServletContext servletcontext = (ServletContext)context.getExternalContext().getContext();
      String imageUsers = servletcontext.getRealPath("/resources/img/users/");
      File dirImageUsers = new File(imageUsers);
      
      if(!dirImageUsers.exists()){
        dirImageUsers.createNewFile();
      }
      
      byte[] bytes = usuario.getImg();
      FileImageOutputStream imageOutput = new FileImageOutputStream(new File(dirImageUsers, usuario.getNome() + "." + EXTENSION));
      imageOutput.write(bytes, 0, bytes.length);
      imageOutput.flush();
      imageOutput.close();
      setImage("/resources/img/users/" + usuario.getNome() + "."+ EXTENSION);
    }catch(Exception e){
      e.printStackTrace();
    }
     
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

  public Part getFile() {
    return file;
  }

  public void setFile(Part file) {
    this.file = file;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }



}
