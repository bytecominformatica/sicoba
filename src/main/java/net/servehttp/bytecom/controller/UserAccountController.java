package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.business.AccountBussiness;
import net.servehttp.bytecom.persistence.entity.security.UserAccount;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.ImageUtil;
import net.servehttp.bytecom.util.Util;

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
  private String page;
  private static final String EXTENSION = "png";
  
  private Part file;

  @Inject
  private AccountBussiness accountBussiness;
  @Inject
  private Util util;
  @Inject
  private ImageUtil imageUtil;

  @PostConstruct
  public void load() {
    listUserAccount = accountBussiness.findUsersAccounts();
    getParameters();
    
  }

  private boolean valida(UserAccount userAccount) {
    boolean result = true;

    if(!accountBussiness.emailAvaliable(userAccount)){
      AlertaUtil.error("Email já cadastrado!");
      result = false;
    }
    return result;
  }

  public String salvar() {
    page = null;
    
    System.out.println("FILE " + file);
    if (valida(userAccount)) {
      if (userAccount.getId() == 0) {
        userAccount.setImg(imageUtil.setThumbnail(imageUtil.tratarImagem(file), EXTENSION));
        accountBussiness.salvar(userAccount);
        AlertaUtil.info("Usuário gravado com sucesso!");
      } else {
        if (file == null) {
          accountBussiness.atualizar(userAccount);
          AlertaUtil.info("Usuário atualizado com sucesso!");
        }else{
          userAccount.setImg(imageUtil.setThumbnail(imageUtil.tratarImagem(file), EXTENSION));
          accountBussiness.atualizar(userAccount);
          AlertaUtil.info("Usuário atualizado com sucesso!");
        }
        
      }
      page = "list";
    }

    return page;
  }
  
 
  private void getParameters() {
    String userAccountId = util.getParameters("id");
    if (userAccountId != null && !userAccountId.isEmpty()) {
      userAccount = accountBussiness.findUserAccountById(Integer.parseInt(userAccountId));
    }
  }

  public String remover() {
    page = null;
    accountBussiness.remover(userAccount);
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



}
