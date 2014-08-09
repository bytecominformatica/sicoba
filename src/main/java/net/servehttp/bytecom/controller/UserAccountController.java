package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.business.AccountBussiness;
import net.servehttp.bytecom.ejb.ProfileImageEJB;
import net.servehttp.bytecom.persistence.entity.security.UserAccount;
import net.servehttp.bytecom.util.AlertaUtil;

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
  private String image;
  private static final String EXTENSION = "png";
  
  private Part file;

  @Inject
  private AccountBussiness accountBussiness;
  @Inject
  private Util util;
  @Inject
  private ProfileImageEJB profileImage;

  public UserAccountController() {}

  @PostConstruct
  public void load() {
    listUserAccount = accountBussiness.findUsersAccounts();
    getParameters();
    if(userAccount.getImg() != null){
      //exibirImagem();
      setImage(profileImage.exibirImagem(userAccount.getImg(), userAccount.getFirstName()));
    }else{
      setImage("/resources/img/default_avatar.png");
    }
  }

  private boolean valida(UserAccount userAccount) {
    boolean result = true;

    if(!accountBussiness.emailAvaliable(userAccount)){
      AlertaUtil.alerta("Email já cadastrado!", AlertaUtil.ERROR);
      result = false;
    }
    return result;
  }

  public String salvar() {
    page = null;
    if (valida(userAccount)) {
      if (userAccount.getId() == 0) {
        userAccount.setImg(profileImage.setThumbnail(profileImage.tratarImagem(file), EXTENSION));
        accountBussiness.salvar(userAccount);
        AlertaUtil.alerta("Usuário gravado com sucesso!");
      } else {
        if (userAccount.getImg() != null) {
          accountBussiness.atualizar(userAccount);
          AlertaUtil.alerta("Usuário atualizado com sucesso!");
        }else{
          userAccount.setImg(profileImage.setThumbnail(profileImage.tratarImagem(file), EXTENSION));
          accountBussiness.atualizar(userAccount);
          AlertaUtil.alerta("Usuário atualizado com sucesso!");
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
