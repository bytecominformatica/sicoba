package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import net.servehttp.bytecom.persistence.UserJPA;
import net.servehttp.bytecom.persistence.entity.security.Authentication;
import net.servehttp.bytecom.persistence.entity.security.UserAccount;

import com.servehttp.bytecom.commons.ImageUtil;

public class AccountBussiness implements Serializable {

  private static final String IMG_USERS = "/img/users/";
  private static final long serialVersionUID = -8296012997453708684L;
  private static final String IMG_DEFAULT = "avatar_male.png";

  @Inject
  private UserJPA jpa;

  public List<UserAccount> findUsersAccounts() {
    return jpa.buscarTodosUserAccount();
  }

  public boolean emailAvaliable(UserAccount userAccount) {
    return jpa.emailAvaliable(userAccount);
  }

  public UserAccount findUserAccountByUsername(String username) {
    return jpa.findUserAccountByUsername(username);
  }

  public void createPictureInSession(UserAccount user) {
    String filename = user.getId() + ".png";

    FacesContext context = FacesContext.getCurrentInstance();
    ServletContext servletcontext = (ServletContext) context.getExternalContext().getContext();
    String folderImages = servletcontext.getRealPath(IMG_USERS);

    String imagemGerada = ImageUtil.exibirImagem(user.getImg(), filename, folderImages);
    if (imagemGerada != null) {
      user.setImageGerada(IMG_USERS + filename);
    } else {
      user.setImageGerada(IMG_USERS + IMG_DEFAULT);
    }
  }

  public UserAccount findUserAccountById(int id) {
    return jpa.buscarPorId(UserAccount.class, id);
  }

  public Authentication findAuthenticationByUserAccount(UserAccount userAccount) {
    return jpa.findAuthenticationByUserAccount(userAccount);
  }

  public <T> T salvar(T t) {
    return jpa.salvar(t);
  }

  public <T> T atualizar(T t) {
    return jpa.atualizar(t);
  }

  public void remover(UserAccount userAccount) {
    jpa.remover(userAccount);
  }

}
