package net.servehttp.bytecom.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.business.AccountBussiness;
import net.servehttp.bytecom.persistence.entity.security.Authentication;
import net.servehttp.bytecom.persistence.entity.security.UserAccount;
import net.servehttp.bytecom.util.Util;
import net.servehttp.bytecom.util.seguranca.HashUtil;
import net.servehttp.bytecom.util.web.AlertaUtil;

import com.servehttp.bytecom.commons.ImageUtil;
import com.servehttp.bytecom.commons.StringUtil;

/**
 * 
 * @author felipe
 *
 */
@Named
@ViewScoped
public class UserAccountController implements Serializable {

  private static final long serialVersionUID = -2081234112300283530L;

  private List<UserAccount> listUserAccount;
  private UserAccount userAccount = new UserAccount();
  private String username;
  private String password;
  private String confirmPassword;
  private String page;
  private static final String EXTENSION = "png";

  private Part file;

  @Inject
  private AccountBussiness business;
  @Inject
  private Util util;

  @PostConstruct
  public void load() {
    listUserAccount = business.findUsersAccounts();
    getParameters();

  }

  private boolean valida(UserAccount userAccount) {
    boolean result = true;

    if (!business.emailAvaliable(userAccount)) {
      AlertaUtil.error("Email já cadastrado!");
      result = false;
    }

    if (password != null && !password.isEmpty() && !password.equals(confirmPassword)) {
      AlertaUtil.error("As senhas não confere");
      return false;
    }
    return result;
  }

  public String salvar() {
    page = null;

    if (valida(userAccount)) {
      if (file != null) {
        try {
          userAccount.setImg(ImageUtil.setThumbnail(ImageUtil.tratarImagem(file.getInputStream()),
              EXTENSION));
        } catch (IOException e) {
          AlertaUtil.error(e.getMessage());
        }
      }
      if (userAccount.getId() == 0) {
        Authentication auth = new Authentication();
        auth.setUsername(username);
        auth.setPassword(HashUtil.sha256ToHex(password));
        auth.setUserAccount(userAccount);
        business.salvar(auth);
        AlertaUtil.info("Usuário gravado com sucesso!");
      } else {
        business.atualizar(userAccount);
        AlertaUtil.info("Usuário atualizado com sucesso!");
      }
      page = "list";
    }

    return page;
  }

  public void resetPassword() {
    Authentication auth = business.findAuthenticationByUserAccount(userAccount);
    String senha = StringUtil.gerarSenha(8);
    auth.setPassword(HashUtil.sha256ToHex(senha));
    business.atualizar(auth);
    AlertaUtil.info("A senha do usuário " + auth.getUsername() + " foi alterada para " + senha);
  }

  private void getParameters() {
    String userAccountId = util.getParameters("id");
    if (userAccountId != null && !userAccountId.isEmpty()) {
      userAccount = business.findUserAccountById(Integer.parseInt(userAccountId));
    }
  }

  public String remover() {
    page = null;
    business.remover(userAccount);
    page = "list";
    return page;
  }

  public List<UserAccount> getListUserAccount() {
    return listUserAccount;
  }

  public void setListUserAccount(List<UserAccount> listUserAccount) {
    this.listUserAccount = listUserAccount;
  }

  public UserAccount getUserAccount() {
    return userAccount;
  }

  public void setUserAccount(UserAccount userAccount) {
    this.userAccount = userAccount;
  }

  public Part getFile() {
    return file;
  }

  public void setFile(Part file) {
    this.file = file;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }



}
