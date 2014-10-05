package net.servehttp.bytecom.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

import net.servehttp.bytecom.persistence.entity.cadastro.EntityGeneric;

import java.util.List;


@Entity
@Table(name = "pais")
public class Pais extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = -8042147881454631916L;

  private String nome;

  @OneToMany(mappedBy = "pais", fetch = FetchType.EAGER)
  private List<Estado> estados;

  public Pais() {}

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

  public List<Estado> getEstados() {
    return this.estados;
  }

  public void setEstados(List<Estado> estados) {
    this.estados = estados;
  }

  public Estado addEstado(Estado estado) {
    getEstados().add(estado);
    estado.setPais(this);

    return estado;
  }

  public Estado removeEstado(Estado estado) {
    getEstados().remove(estado);
    estado.setPais(null);

    return estado;
  }

}
