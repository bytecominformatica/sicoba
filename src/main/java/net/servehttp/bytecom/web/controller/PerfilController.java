package net.servehttp.bytecom.web.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.business.AccountBussiness;
import net.servehttp.bytecom.persistence.entity.security.Authentication;
import net.servehttp.bytecom.persistence.entity.security.UserAccount;
import net.servehttp.bytecom.qualifier.UsuarioLogado;
import net.servehttp.bytecom.util.seguranca.HashUtil;
import net.servehttp.bytecom.util.web.AlertaUtil;

import com.servehttp.bytecom.commons.ImageUtil;

/**
 * 
 * @author Clairton Luz - clairton.c.l@gmail.com
 *
 */
@Named
@ViewScoped
public class PerfilController implements Serializable {

  private static final long serialVersionUID = -2081234112300283530L;
  @Inject
  @UsuarioLogado
  private UserAccount userAccount;
  @Inject
  private AccountBussiness accountBussiness;
  private Authentication authentication;
  private String password;
  private String confirmPassword;
  private Part file;

  @PostConstruct
  public void load() {
    setAuthentication(accountBussiness.findAuthenticationByUserAccount(userAccount));
  }

  public void carregarImagem() {
    if (file != null) {
      try {
        userAccount.setImg(ImageUtil.setThumbnail(ImageUtil.tratarImagem(file.getInputStream()), "png"));
        userAccount.setImageGerada(null);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean userAccountValid() {
    boolean result = true;
    if (!accountBussiness.emailAvaliable(userAccount)) {
      AlertaUtil.error("Email já cadastrado!");
      result = false;
    }
    if (!senhaConfere()) {
      AlertaUtil.error("Senha não confere!");
      result = false;
    }
    return result;
  }

  public void salvar() {
    if (userAccountValid()) {
      if (password.isEmpty()) {
        accountBussiness.atualizar(userAccount);
      } else {
        authentication.setUserAccount(userAccount);
        authentication.setPassword(HashUtil.sha256ToHex(password));
        accountBussiness.atualizar(authentication);
      }
      AlertaUtil.info("Usuário atualizado com sucesso!");
    }
  }

  private boolean senhaConfere() {
    return password.equals(confirmPassword) ;
  }

  public UserAccount getUserAccount() {
    return userAccount;
  }

  public void setUserAccount(UserAccount userAccount) {
    this.userAccount = userAccount;
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

  public Part getFile() {
    return file;
  }

  public void setFile(Part file) {
    this.file = file;
  }

  public Authentication getAuthentication() {
    return authentication;
  }

  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

}
