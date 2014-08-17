package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "tecnico")
public class Tecnico extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -4731640193484264555L;

  @Size(max = 255, message = "nome tamanho máximo de 255 caracteres!")
  private String nome;
  @Size(max = 10, message = "fone deve possuir 10 digitos ex: 9999999999")
  private String fone;
  @Email(message = "Email inválido")
  private String email;

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

}
