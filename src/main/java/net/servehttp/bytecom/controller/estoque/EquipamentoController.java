package net.servehttp.bytecom.controller.estoque;

import net.servehttp.bytecom.controller.extra.GenericoController;
import net.servehttp.bytecom.model.jpa.entity.estoque.Equipamento;
import net.servehttp.bytecom.model.jpa.estoque.EquipamentoJPA;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class EquipamentoController extends GenericoController {

    private static final long serialVersionUID = 8291411734476446041L;
    private List<Equipamento> listEquipamentos;
    private Equipamento equipamento = new Equipamento();
    @Inject
    private EquipamentoJPA equipamentoJPA;

    @PostConstruct
    public void load() {
        listEquipamentos = equipamentoJPA.buscarTodosEquipamento();
        getParameters();
    }


    private void getParameters() {
        String id = WebUtil.getParameters("id");
        if (id != null && !id.isEmpty()) {
            equipamento = equipamentoJPA.buscarPorId(Integer.valueOf(id));
        }
    }

    public List<Equipamento> getListEquipamentos() {
        return listEquipamentos;
    }

    public void setListEquipamentos(List<Equipamento> listEquipamentos) {
        this.listEquipamentos = listEquipamentos;
    }

    public String salvar() {
        String page = null;
        if (isValido(equipamento)) {
            jpa.salvar(equipamento);
            AlertaUtil.info("Salvo com sucesso!");
            load();
            page = "list";
        }
        return page;
    }

    public boolean isValido(Equipamento e) {
        boolean valido = true;
        Equipamento equipamentoEncontrado = equipamentoJPA.buscarEquipamentoPorMac(e.getMac());
        if (equipamentoEncontrado != null && equipamentoEncontrado.getId() != e.getId()) {
            AlertaUtil.error("MAC já Cadastrado");
            valido = false;
        }
        return valido;
    }

    public String remover() {
        equipamentoJPA.remover(equipamento);
        load();
        AlertaUtil.info("Equipamento removido com sucesso!");
        return "list";
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

}
