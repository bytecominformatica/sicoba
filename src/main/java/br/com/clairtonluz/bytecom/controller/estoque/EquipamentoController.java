package br.com.clairtonluz.bytecom.controller.estoque;

import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.jpa.estoque.EquipamentoJPA;
import br.com.clairtonluz.bytecom.service.estoque.EquipamentoService;
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
public class EquipamentoController implements Serializable {

    private static final long serialVersionUID = 8291411734476446041L;
    private List<Equipamento> listEquipamentos;
    private Equipamento equipamento = new Equipamento();
    @Inject
    private EquipamentoService equipamentoService;

    @PostConstruct
    public void load() {
        listEquipamentos = equipamentoService.buscarTodos();
        getParameters();
    }


    private void getParameters() {
        String id = WebUtil.getParameters("id");
        if (id != null && !id.isEmpty()) {
            equipamento = equipamentoService.buscarPorId(Integer.valueOf(id));
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
            equipamentoService.save(equipamento);
            AlertaUtil.info("Salvo com sucesso!");
            load();
            page = "list";
        }
        return page;
    }

    public boolean isValido(Equipamento e) {
        boolean valido = true;
        Equipamento equipamentoEncontrado = equipamentoService.buscarPorMac(e.getMac());
        if (equipamentoEncontrado != null && equipamentoEncontrado.getId() != e.getId()) {
            AlertaUtil.error("MAC já Cadastrado");
            valido = false;
        }
        return valido;
    }

    public String remover() {
        equipamentoService.remove(equipamento);
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
