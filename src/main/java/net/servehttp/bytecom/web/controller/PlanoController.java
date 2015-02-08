package net.servehttp.bytecom.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.business.PlanoBussiness;
import net.servehttp.bytecom.persistence.entity.cadastro.Plano;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

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
  private PlanoBussiness planoBussiness;

  @PostConstruct
  public void load() {
    listPlanos = planoBussiness.findAll();
    getParameters();
  }

  private void getParameters() {
    String planoId = WebUtil.getParameters("id");
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
        AlertaUtil.info("Salvo com sucesso!");
      } else {
        planoBussiness.atualizar(getPlano());
        AlertaUtil.info("Atualizado com sucesso!");
      }
      load();
      setPlano(new Plano());
      page = "list";
    } else {
      AlertaUtil.error("Plano já cadastrado!");
    }
    return page;
  }


  public String remover() {
    String page = null;
    if (planoBussiness.planWithoutUse(getPlano())) {
      planoBussiness.remover(getPlano());
      load();
      AlertaUtil.info("Removido com sucesso!");
      page = "list";
    } else {
      AlertaUtil.error("Não é possível remover este plano, pois o mesmo está em uso!");
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
