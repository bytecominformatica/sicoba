package net.servehttp.bytecom.persistence.jpa.entity.comercial;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;

@Entity
@Table(name = "cidade")
public class Cidade extends EntityGeneric {
  private static final long serialVersionUID = -4732281989184639857L;

  private String nome;

  @OneToMany(mappedBy = "cidade", fetch = FetchType.EAGER)
  private List<Bairro> bairros;

  @ManyToOne(fetch = FetchType.EAGER)
  private Estado estado;

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
