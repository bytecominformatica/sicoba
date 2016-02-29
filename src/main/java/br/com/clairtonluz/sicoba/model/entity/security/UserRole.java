package br.com.clairtonluz.sicoba.model.entity.security;

import br.com.clairtonluz.sicoba.model.entity.extra.EntityGeneric;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author clairton
 */
@Entity
@Table(name = "user_roles")
public class UserRole extends EntityGeneric {

    @NotNull(message = "role é obrigatório")
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
