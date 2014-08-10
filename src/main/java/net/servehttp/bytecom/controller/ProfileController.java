package net.servehttp.bytecom.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.business.AccountBussiness;
import net.servehttp.bytecom.persistence.entity.security.UserAccount;
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
  private UserAccount userAccount;

  @Inject
  private AccountBussiness accountBussiness;

  private ProducesUtil producesUtil = new ProducesUtil();


  public ProfileController() {}

  @PostConstruct
  public void load() {
    userAccount = producesUtil.getUserAccount();
  }

  private boolean valida(UserAccount userAccount) {
    boolean result = true;
    if (!accountBussiness.emailAvaliable(userAccount)) {
      AlertaUtil.error("Email já cadastrado!");
      result = false;
    }
    return result;
  }

  public String salvar() {
    String page = null;
    if (valida(userAccount)) {
      if (userAccount.getId() == 0) {
        accountBussiness.salvar(userAccount);
        AlertaUtil.info("Usuário gravado com sucesso!");
      } else {
        accountBussiness.atualizar(userAccount);
        AlertaUtil.info("Usuário atualizado com sucesso!");
      }
      page = "list";
    }

    return page;
  }


  public UserAccount getUserAccount() {
    return userAccount;
  }

  public void setUserAccount(UserAccount userAccount) {
    this.userAccount = userAccount;
  }

}
