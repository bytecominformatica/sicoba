package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.servehttp.bytecom.annotation.CpfCnpj;
import net.servehttp.bytecom.persistence.entity.Endereco;

/**
 * 
 * @author clairton
 */
@Entity
@Table(name = "fornecedor")
public class Fornecedor extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -4003398104522589791L;
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

  public Fornecedor() {
    this.endereco = new Endereco();
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

}
