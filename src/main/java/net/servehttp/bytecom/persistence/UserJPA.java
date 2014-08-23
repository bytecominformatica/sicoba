package net.servehttp.bytecom.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.security.Authentication;
import net.servehttp.bytecom.persistence.entity.security.UserAccount;

/**
 * 
 * @author clairton
 */
@Transactional
public class UserJPA implements Serializable {

  private static final long serialVersionUID = -1412149982160690889L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public boolean emailAvaliable(UserAccount userAccount) {
    UserAccount u = findUserAccountByEmail(userAccount.getEmail());
    return u == null || u.getId() == userAccount.getId();
  }

  public boolean loginAvaliable(Authentication authentication) {
    Authentication a;
    try {
      a =
          em.createQuery("select a from Authentication a where a.username = :username",
              Authentication.class).setParameter("username", authentication.getUsername())
              .getSingleResult();
    } catch (NoResultException e) {
      a = null;
    }
    return a == null || a.getId() == authentication.getId();
  }

  private UserAccount findUserAccountByEmail(String email) {
    try {
      return em
          .createQuery("select u from UserAccount u where u.email = :email", UserAccount.class)
          .setParameter("email", email).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public UserAccount findUserAccountByUsername(String username) {
    try {
      return em
          .createQuery("select a.userAccount from Authentication a where a.username = :username",
              UserAccount.class).setParameter("username", username).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public Authentication findAuthenticationByUserAccount(UserAccount userAccount) {
    return em.createQuery("select a from Authentication a where a.userAccount.id = :id",
            Authentication.class).setParameter("id", userAccount.getId()).getSingleResult();
  }

}
