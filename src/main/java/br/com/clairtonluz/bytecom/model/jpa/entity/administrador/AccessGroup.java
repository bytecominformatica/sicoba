package br.com.clairtonluz.bytecom.model.jpa.entity.administrador;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author clairtonluz
 */
@Entity
@Table(name = "access_group")
public class AccessGroup extends EntityGeneric {

    private static final long serialVersionUID = 7776068215045578731L;
    private String name;
    private String description;
    @Column(name = "user_default")
    private boolean userDefault;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUserDefault() {
        return userDefault;
    }

    public void setUserDefault(boolean userDefault) {
        this.userDefault = userDefault;
    }

    @Override
    public String toString() {
        return name;
    }


}
