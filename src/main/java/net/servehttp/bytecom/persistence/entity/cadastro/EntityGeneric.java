package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class EntityGeneric implements Serializable {

  private static final long serialVersionUID = -7494177824158985929L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected int id;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  protected Calendar createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  protected Calendar updatedAt;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
}
