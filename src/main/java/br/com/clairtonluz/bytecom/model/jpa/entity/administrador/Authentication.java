package br.com.clairtonluz.bytecom.model.jpa.entity.administrador;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author clairtonluz
 */
@Entity
@Table(name = "authentication")
public class Authentication extends EntityGeneric {

    private static final long serialVersionUID = 6300355473032048549L;
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

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

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
