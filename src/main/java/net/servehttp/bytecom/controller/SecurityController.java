package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.entity.security.UserAccount;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.WebUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * 
 * @author Clairton Luz - clairton.c.l@gmail.com
 *
 */
@Named
@RequestScoped
public class SecurityController implements Serializable {

  private static final long serialVersionUID = -4657746545855537894L;
  private static final Logger LOGGER = Logger.getLogger(SecurityController.class.getSimpleName());
  public static final String HOME_URL = "/index.xhtml";

  private String username;
  private String password;
  private boolean remember;

  private Subject currentUser = SecurityUtils.getSubject();

  @Inject
  private WebUtil webUtil;

  public void authenticate() {
    if (!currentUser.isAuthenticated()) {
      try {
        currentUser.login(new UsernamePasswordToken(username, password, remember));
//        UserAccount userAccount = gene
//        currentUser.getSession().setAttribute("accountUser", value);
        
        webUtil.redirect(HOME_URL);

      } catch (AuthenticationException e) {
        AlertaUtil.alerta("Unknown user, please try again");
        LOGGER.info("[" + new Date() + "] - " + "[" + username + "] - " + "ACESSO NEGADO");
      }
    } else {
      System.out.println("autenticado");

    }
  }

  public void logout() {
    SecurityUtils.getSubject().logout();
    webUtil.redirect(HOME_URL);
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

}
