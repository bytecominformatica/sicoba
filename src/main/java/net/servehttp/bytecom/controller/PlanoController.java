package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.business.PlanoBussiness;
import net.servehttp.bytecom.persistence.entity.cadastro.Plano;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class PlanoController implements Serializable {

  private static final long serialVersionUID = -676355663109515972L;
  private List<Plano> listPlanos;
  @Inject
  private Plano plano;
  @Inject
  private Util util;
  @Inject
  private PlanoBussiness planoBussiness;

  @PostConstruct
  public void load() {
    listPlanos = planoBussiness.findPlans();
    getParameters();
  }

  private void getParameters() {
    String planoId = util.getParameters("planoId");
    if (planoId != null && !planoId.isEmpty()) {
      setPlano(planoBussiness.findById(Integer.parseInt(planoId)));
    }
  }

  public List<Plano> getListPlanos() {
    return listPlanos;
  }

  public void setListPlanos(List<Plano> listPlanos) {
    this.listPlanos = listPlanos;
  }

  public String salvar() {
    String page = null;
    if (planoBussiness.planAvaliable(getPlano())) {
      if (getPlano().getId() == 0) {
        planoBussiness.salvar(getPlano());
        AlertaUtil.alerta("Plano adicionado com sucesso!");
      } else {
        planoBussiness.atualizar(getPlano());
        AlertaUtil.alerta("Atualizado com sucesso!");
      }
      load();
      setPlano(new Plano());
      page = "list";
    } else {
      AlertaUtil.alerta("Plano já cadastrado!", AlertaUtil.ERROR);
    }
    return page;
  }


  public String remover() {
    String page = null;
    if (planoBussiness.planWithoutUse(getPlano())) {
      planoBussiness.remover(getPlano());
      load();
      AlertaUtil.alerta("Removido com sucesso!");
      page = "list";
    } else {
      AlertaUtil.alerta("Não é possível remover este plano, pois o mesmo está em uso!",
          AlertaUtil.ERROR);
    }
    return page;
  }

  public Plano getPlano() {
    return plano;
  }

  public void setPlano(Plano plano) {
    this.plano = plano;
  }

}
