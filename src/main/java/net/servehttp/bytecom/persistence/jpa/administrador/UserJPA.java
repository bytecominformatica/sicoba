package net.servehttp.bytecom.persistence.jpa.administrador;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.jpa.entity.administrador.Authentication;
import net.servehttp.bytecom.persistence.jpa.entity.administrador.QAuthentication;
import net.servehttp.bytecom.persistence.jpa.entity.administrador.QUserAccount;
import net.servehttp.bytecom.persistence.jpa.entity.administrador.UserAccount;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
 */
@Transactional
public class UserJPA implements Serializable {

  private static final long serialVersionUID = -1412149982160690889L;
  @Inject
  protected EntityManager em;
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public boolean emailAvaliable(UserAccount userAccount) {
    UserAccount u = findUserAccountByEmail(userAccount.getEmail());
    return u == null || u.getId() == userAccount.getId();
  }

  public boolean loginAvaliable(Authentication authentication) {
    QAuthentication au = QAuthentication.authentication;
    Authentication a =
        new JPAQuery(em).from(au).where(au.username.eq(authentication.getUsername()))
            .uniqueResult(au);
    return a == null || a.getId() == authentication.getId();
  }

  private UserAccount findUserAccountByEmail(String email) {
    QUserAccount ua = QUserAccount.userAccount;
    return new JPAQuery(em).from(ua).where(ua.email.eq(email)).uniqueResult(ua);
  }

  public UserAccount findUserAccountByUsername(String username) {
    QAuthentication a = QAuthentication.authentication;
    Authentication authentication =
        new JPAQuery(em).from(a).where(a.username.eq(username)).uniqueResult(a);
    if (authentication != null) {
      return authentication.getUserAccount();
    } else {
      return null;
    }
  }

  public Authentication findAuthenticationByUserAccount(UserAccount userAccount) {
    QAuthentication a = QAuthentication.authentication;
    return new JPAQuery(em).from(a).where(a.userAccount.id.eq(userAccount.getId())).uniqueResult(a);
  }

  public List<UserAccount> buscarTodosUserAccount() {
    QUserAccount ua = QUserAccount.userAccount;
    return new JPAQuery(em).from(ua).list(ua);
  }

}
