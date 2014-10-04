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

public class AccountBussiness extends genericoBusiness implements Serializable {

  private static final String IMG_USERS = "/img/users/";
  private static final long serialVersionUID = -8296012997453708684L;
  private static final String IMG_DEFAULT = "avatar_male.png";

  @Inject
  private UserJPA userJPA;

  public List<UserAccount> findUsersAccounts() {
    return genericoJPA.buscarTodos(UserAccount.class);
  }

  public boolean emailAvaliable(UserAccount userAccount) {
    return userJPA.emailAvaliable(userAccount);
  }

  public UserAccount buscarUsuarioPorUsername(String username) {
    return userJPA.findUserAccountByUsername(username);
  }

  public void criarImagemNaSessao(UserAccount user) {
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
    return genericoJPA.findById(UserAccount.class, id);
  }

  public Authentication findAuthenticationByUserAccount(UserAccount userAccount) {
    return userJPA.findAuthenticationByUserAccount(userAccount);
  }

}
