package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.PlanoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Contrato;
import net.servehttp.bytecom.persistence.entity.cadastro.Plano;

public class PlanoBussiness implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;
  @Inject
  private PlanoJPA planoJPA;

  public List<Plano> findAll() {
    return planoJPA.buscarTodos(Plano.class, true, "nome", 2000);
  }

  public boolean planAvaliable(Plano plano) {
    List<Plano> plans = planoJPA.buscarTodos("nome", plano.getNome(), Plano.class);
    return plans.isEmpty() || plans.get(0).getId() == plano.getId();
  }

  public boolean planWithoutUse(Plano plano){
    return !planoJPA.buscarTodos("plano", plano, Contrato.class).isEmpty();
  }

  public Plano buscarPorId(int id) {
    return planoJPA.buscarPorId(Plano.class, id);
  }

  public <T> T salvar(T t) {
    return planoJPA.salvar(t);
  }

  public <T> T atualizar(T t) {
    return planoJPA.atualizar(t);
  }

  public void remover(Plano plano) {
    planoJPA.remover(plano);
    
  }

}
