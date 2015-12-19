package br.com.clairtonluz.bytecom.model.jpa.administrador;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.administrador.QUserAccount;
import br.com.clairtonluz.bytecom.model.jpa.entity.administrador.UserAccount;
import com.mysema.query.jpa.impl.JPAQuery;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class UserAccountJPA extends CrudJPA {

    private static final long serialVersionUID = 1857140370479772238L;
    private QUserAccount ua = QUserAccount.userAccount;

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public UserAccount buscarPorId(int id) {
        return entityManager.find(UserAccount.class, id);
    }

    public List<UserAccount> buscarTodos() {
        return new JPAQuery(entityManager).from(ua).orderBy(ua.firstName.asc()).list(ua);
    }

}
