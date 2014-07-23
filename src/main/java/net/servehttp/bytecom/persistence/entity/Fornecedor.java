package net.servehttp.bytecom.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.servehttp.bytecom.annotation.CpfCnpj;

/**
 * 
 * @author clairton
 */
@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable {

  private static final long serialVersionUID = -4003398104522589791L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String nome;
  @Column(name = "razao_social")
  private String razaoSocial;
  private String fone;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id")
  private Endereco endereco;
  
  @Column(name = "cpf_cnpj")
  @CpfCnpj
  private String cpfCnpj;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Calendar createdAt;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Calendar updateAt;
  
  public Fornecedor(){
    this.endereco = new Endereco();
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
  public String getRazaoSocial() {
    return razaoSocial;
  }
  public void setRazaoSocial(String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }
  public String getFone() {
    return fone;
  }
  public void setFone(String fone) {
    this.fone = fone;
  }
  public Endereco getEndereco() {
    return endereco;
  }
  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }
  public String getCpfCnpj() {
    return cpfCnpj;
  }
  public void setCpfCnpj(String cpfCnpj) {
    this.cpfCnpj = cpfCnpj;
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
