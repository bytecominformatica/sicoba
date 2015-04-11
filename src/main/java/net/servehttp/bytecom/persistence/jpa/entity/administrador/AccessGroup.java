package net.servehttp.bytecom.persistence.jpa.entity.administrador;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;

/**
 * 
 * @author Clairton Luz - clairton.c.l@gmail.com
 *
 */
@Entity
@Table(name = "access_group")
public class AccessGroup extends EntityGeneric implements Serializable {

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
