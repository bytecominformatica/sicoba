package net.servehttp.bytecom.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.servehttp.bytecom.persistence.entity.security.UserAccount;
import net.servehttp.bytecom.util.AlertaUtil;


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
//
//  @Inject
//  private UserAccount userAccount;
//  @Inject
//  private HttpServletRequest request;
//  @Inject
//  private HttpServletResponse response;
//
//  private String username;
//  private String password;
//
//  private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//  
//  public boolean isUserSignedIn() {
//    return sessionMap.containsKey("signedUser");
//  }
//
//  public String login() {
//    return "/login?faces-redirect=true";
//  }
//
//  public String authenticate() {
//    System.out.println(authenticate());
//    try {
//      System.out.println("aaa " + request.authenticate(response));
//      if (!isUserSignedIn()) {
//        System.out.println("USUARIO = " + username);
//        System.out.println("SENHA = " + password);
//        request.login(this.getUsername(), this.getPassword());
//      }
//      return "/index";
//    } catch (ServletException e) {
//      LOGGER.log(Level.WARNING, e.getMessage(), e);
//      AlertaUtil.alerta("Usuário e/ou senha inválida!", AlertaUtil.ERROR);
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    return "/login";
//  }
//  
//  public String logout() {
//    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//    try {
//        request.logout();
//        session.invalidate();
//    } catch(ServletException se) {
//        LOGGER.log(Level.INFO, se.getMessage(), se);
//        return "/index?faces-redirect=true";
//    }
//    return "/index?faces-redirect=true";
//}
//
//  public String getUsername() {
//    return username;
//  }
//
//  public void setUsername(String username) {
//    this.username = username;
//  }
//
//  public String getPassword() {
//    return password;
//  }
//
//  public void setPassword(String password) {
//    this.password = password;
//  }
//
//  public UserAccount getUserAccount() {
//    return userAccount;
//  }
//
//  public void setUserAccount(UserAccount userAccount) {
//    this.userAccount = userAccount;
//  }


}
