package net.servehttp.bytecom.persistence.entity.security;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.servehttp.bytecom.util.ImageUtil;

/**
 * 
 * @author Clairton Luz - clairton.c.l@gmail.com
 *
 */
@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable {

  private static final long serialVersionUID = -7710346689149270175L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String email;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @ManyToMany
  @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id",
      referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "group_id",
      referencedColumnName = "id"))
  private List<AccessGroup> accessGroup;
  @Lob
  private byte[] img;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar updatedAt;

  @Transient
  private ImageUtil imageUtil = new ImageUtil();
  @Transient
  private String imageGerada;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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

  public String getImageGerada() {
    if (imageGerada == null) {
      if (img != null) {
        imageGerada = imageUtil.exibirImagem(img, System.currentTimeMillis());
      } else {
        imageGerada = "/img/users/avatar_male.png";
      }
    }
    return imageGerada;
  }

  public void setImageGerada(String imageGerada) {
    this.imageGerada = imageGerada;
  }

}
