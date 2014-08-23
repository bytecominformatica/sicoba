package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.UserJPA;
import net.servehttp.bytecom.persistence.entity.security.Authentication;
import net.servehttp.bytecom.persistence.entity.security.UserAccount;
import net.servehttp.bytecom.util.ImageUtil;

public class AccountBussiness extends genericoBusiness implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;

  @Inject
  private UserJPA userJPA;
  @Inject
  private ImageUtil imageUtil;

  public List<UserAccount> findUsersAccounts() {
    return genericoJPA.buscarTodos(UserAccount.class);
  }

  public boolean emailAvaliable(UserAccount userAccount) {
    return userJPA.emailAvaliable(userAccount);
  }

  public UserAccount findUserAccountByUsername(String username) {
    return userJPA.findUserAccountByUsername(username);
  }

  public UserAccount findUserAccountById(int id) {
    return genericoJPA.findById(UserAccount.class, id);
  }

  public Authentication findAuthenticationByUserAccount(UserAccount userAccount) {
    return userJPA.findAuthenticationByUserAccount(userAccount);
  }

}
