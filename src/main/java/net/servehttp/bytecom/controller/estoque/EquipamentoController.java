package net.servehttp.bytecom.controller.estoque;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.estoque.jpa.EquipamentoJPA;
import net.servehttp.bytecom.estoque.jpa.entity.Equipamento;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class EquipamentoController implements Serializable {

  private static final long serialVersionUID = 8291411734476446041L;
  private List<Equipamento> listEquipamentos;
  private Equipamento equipamento = new Equipamento();
  @Inject
  private EquipamentoJPA jpa;

  @PostConstruct
  public void load() {
    listEquipamentos = jpa.buscarTodosEquipamento();
    getParameters();
  }


  private void getParameters() {
    String id = WebUtil.getParameters("id");
    if (id != null && !id.isEmpty()) {
      equipamento = jpa.buscarPorId(Integer.valueOf(id));
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
      if (equipamento.getId() == 0) {
        jpa.salvar(equipamento);
        AlertaUtil.info("Equipamento adicionado com sucesso!");
      } else {
        jpa.atualizar(equipamento);
        AlertaUtil.info("Equipamento atualizado com sucesso!");

      }
      load();
      page = "list";
    }
    return page;
  }

  public boolean isValido(Equipamento e) {
    boolean valido = true;
    Equipamento equipamentoEncontrado = jpa.buscarEquipamentoPorMac(e.getMac());
    if (equipamentoEncontrado != null && equipamentoEncontrado.getId() != e.getId()) {
      AlertaUtil.error("MAC já Cadastrado");
      valido = false;
    }
    return valido;
  }

  public String remover() {
    jpa.remover(equipamento);
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
