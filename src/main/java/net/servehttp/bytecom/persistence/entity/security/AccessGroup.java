package net.servehttp.bytecom.persistence.entity.security;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.servehttp.bytecom.persistence.entity.cadastro.EntityGeneric;

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

  public Calendar getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Calendar createdAt) {
    this.createdAt = createdAt;
  }

  public Calendar getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Calendar updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return name;
  }
  
  

}
