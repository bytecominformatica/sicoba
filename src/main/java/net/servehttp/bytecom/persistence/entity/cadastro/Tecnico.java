package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "tecnico")
public class Tecnico implements Serializable {

  private static final long serialVersionUID = -4731640193484264555L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Size(max=255, message="nome tamanho máximo de 255 caracteres!")
  private String nome;
  @Size(max=10, message="fone deve possuir 10 digitos ex: 9999999999")
  private String fone;
  @Email(message="Email inválido")
  private String email;
  
  @Column(name = "created_at")
  private Calendar createdAt;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Calendar updateAt;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getFone() {
    return fone;
  }
  public void setFone(String fone) {
    this.fone = fone;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public Calendar getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(Calendar createdAt) {
    this.createdAt = createdAt;
  }
  public Calendar getUpdateAt() {
    return updateAt;
  }
  public void setUpdateAt(Calendar updateAt) {
    this.updateAt = updateAt;
  }
  
  

}
