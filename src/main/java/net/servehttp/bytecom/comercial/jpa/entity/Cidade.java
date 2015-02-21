package net.servehttp.bytecom.comercial.jpa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.servehttp.bytecom.extra.jpa.entity.EntityGeneric;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "cidade")
public class Cidade extends EntityGeneric implements Serializable {
  private static final long serialVersionUID = -4732281989184639857L;

  private String nome;

  @XmlTransient
  @OneToMany(mappedBy = "cidade", fetch = FetchType.EAGER)
  private List<Bairro> bairros;

  @ManyToOne(fetch = FetchType.EAGER)
  private Estado estado;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public List<Bairro> getBairros() {
    return this.bairros;
  }

  public void setBairros(List<Bairro> bairros) {
    this.bairros = bairros;
  }

  public Bairro addBairro(Bairro bairro) {
    getBairros().add(bairro);
    bairro.setCidade(this);

    return bairro;
  }

  public Bairro removeBairro(Bairro bairro) {
    getBairros().remove(bairro);
    bairro.setCidade(null);

    return bairro;
  }

  public Estado getEstado() {
    return this.estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public String toString() {
    return nome;
  }

}
