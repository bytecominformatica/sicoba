package net.servehttp.bytecom.controller.comercial;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.controller.extra.GenericoController;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Plano;
import net.servehttp.bytecom.service.comercial.PlanoService;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class PlanoController extends GenericoController implements Serializable {

  private static final long serialVersionUID = -676355663109515972L;
  private List<Plano> listPlanos;
  @Inject
  private Plano plano;
  @Inject
  private PlanoService planoService;

  @PostConstruct
  public void load() {
    listPlanos = planoService.findAll();
    getParameters();
  }

  private void getParameters() {
    String planoId = WebUtil.getParameters("id");
    if (planoId != null && !planoId.isEmpty()) {
      setPlano(planoService.buscarPorId(Integer.parseInt(planoId)));
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
    if (planoService.planAvaliable(getPlano())) {
      jpa.salvar(getPlano());
      AlertaUtil.info("Salvo com sucesso!");
      load();
      plano = new Plano();
      page = "list";
    } else {
      AlertaUtil.error("Plano já cadastrado!");
    }
    return page;
  }


  public String remover() {
    String page = null;
    if (planoService.planWithoutUse(plano)) {
      planoService.remover(plano);
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
