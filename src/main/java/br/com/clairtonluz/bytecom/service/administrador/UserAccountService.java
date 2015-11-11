package br.com.clairtonluz.bytecom.service.administrador;

import br.com.clairtonluz.bytecom.model.jpa.administrador.UserAccountJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.administrador.UserAccount;
import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class UserAccountService implements Serializable {

    private static final long serialVersionUID = -8296012997453708684L;
    @Inject
    private UserAccountJPA userAccountJPA;

    public List<UserAccount> findAll() {
        return userAccountJPA.buscarTodos();
    }

    public UserAccount buscarPorId(int id) {
        return userAccountJPA.buscarPorId(id);
    }

    public void remover(UserAccount userAccount) {
        userAccountJPA.remove(userAccount);
    }

    public EntityGeneric save(EntityGeneric entityGeneric) {
        return userAccountJPA.save(entityGeneric);
    }
}
