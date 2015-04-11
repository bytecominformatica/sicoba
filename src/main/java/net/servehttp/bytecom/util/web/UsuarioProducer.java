package net.servehttp.bytecom.util.web;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.jpa.entity.administrador.UserAccount;
import net.servehttp.bytecom.util.qualifier.UsuarioLogado;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author Clairton Luz
 */
public class UsuarioProducer {

  @Produces
  @Named
  @UsuarioLogado
  public UserAccount getUserAccount() {
    Subject currentUser = SecurityUtils.getSubject();
    return (UserAccount) currentUser.getSession().getAttribute("currentUser");
  }

}
