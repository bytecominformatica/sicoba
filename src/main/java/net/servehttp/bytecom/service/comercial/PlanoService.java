package net.servehttp.bytecom.service.comercial;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Contrato;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Plano;
import net.servehttp.bytecom.persistence.jpa.extra.GenericoJPA;

public class PlanoService implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;
  @Inject
  private GenericoJPA jpa;

  public List<Plano> findAll() {
    return jpa.buscarTodos(Plano.class, true, "nome", 2000);
  }

  public boolean planAvaliable(Plano plano) {
    List<Plano> plans = jpa.buscarTodos("nome", plano.getNome(), Plano.class);
    return plans.isEmpty() || plans.get(0).getId() == plano.getId();
  }

  public boolean planWithoutUse(Plano plano){
    return !jpa.buscarTodos("plano", plano, Contrato.class).isEmpty();
  }

  public Plano buscarPorId(int id) {
    return jpa.buscarPorId(Plano.class, id);
  }

  public void remover(Plano plano) {
    jpa.remover(plano);
    
  }

}
