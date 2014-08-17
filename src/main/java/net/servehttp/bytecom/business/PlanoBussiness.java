package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import net.servehttp.bytecom.persistence.entity.cadastro.Contrato;
import net.servehttp.bytecom.persistence.entity.cadastro.Plano;

public class PlanoBussiness extends genericoBusiness implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;

  public List<Plano> findAll() {
    return genericoJPA.buscarTodos(Plano.class, true, "nome", 2000);
  }

  public boolean planAvaliable(Plano plano) {
    List<Plano> plans = genericoJPA.buscarTodos("nome", plano.getNome(), Plano.class);
    return plans.isEmpty() || plans.get(0).getId() == plano.getId();
  }

  public boolean planWithoutUse(Plano plano){
    return !genericoJPA.buscarTodos("plano", plano, Contrato.class).isEmpty();
  }

  public Plano findById(int id) {
    return genericoJPA.findById(Plano.class, id);
  }
}
