package br.com.clairtonluz.bytecom.model.jpa.entity.administrador;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author clairtonluz
 */
@Entity
@Table(name = "user_account")
public class UserAccount extends EntityGeneric {

    private static final long serialVersionUID = -7710346689149270175L;
    @Email(message = "Email inválido")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id",
            referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "group_id",
            referencedColumnName = "id"))
    private List<AccessGroup> accessGroup;
    @Lob
    private byte[] img;

    @Transient
    private String imageGerada;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<AccessGroup> getAccessGroup() {
        return accessGroup;
    }

    public void setAccessGroup(List<AccessGroup> accessGroup) {
        this.accessGroup = accessGroup;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImageGerada() {
        return imageGerada;
    }

    public void setImageGerada(String imageGerada) {
        this.imageGerada = imageGerada;
    }

}
