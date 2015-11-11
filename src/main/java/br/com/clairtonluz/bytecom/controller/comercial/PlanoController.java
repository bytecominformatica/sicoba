package br.com.clairtonluz.bytecom.controller.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.service.comercial.PlanoService;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
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
            planoService.save(getPlano());
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
            AlertaUtil.error("Não é possível remove este plano, pois o mesmo está entityManager uso!");
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
