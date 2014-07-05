package net.servehttp.bytecom.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import net.servehttp.bytecom.util.HashSHA256Util;

/**
 *
 * @author clairton
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String login;
    @Email(message="Email inválido")
    private String email;
    @Basic(optional = false)
    @NotNull
    private String senha;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;

    public Usuario() {
    }

    public Usuario(int id) {
        this.id = id;
    }

    public Usuario(int id, String nome, String login, String senha, Calendar updatedAt) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.updatedAt = updatedAt;
    }


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

    public String getLogin() {
      return login;
    }

    public void setLogin(String login) {
      this.login = login;
    }

    public String getSenha() {
      return senha;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public void setSenha(String senha) {
      this.senha = HashSHA256Util.geraHash256(senha);
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

    /*@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }*/

    @Override
    public String toString() {
        return "br.com.bytecom.entity.Login[ id=" + id + " ]";
    }
    
}
