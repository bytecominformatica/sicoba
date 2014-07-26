package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Equipamento;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class EquipamentoController implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8291411734476446041L;
  private List<Equipamento> listEquipamentos;
  private Equipamento equipamento = new Equipamento();
  private String equipamentoId;
  @Inject
  private GenericoJPA genericoJPA;

  public EquipamentoController() {}

  @PostConstruct
  public void load() {
    listEquipamentos = genericoJPA.buscarTodos(Equipamento.class);
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
        genericoJPA.salvar(equipamento);
        AlertaUtil.alerta("Equipamento adicionado com sucesso!");
      } else {
        genericoJPA.atualizar(equipamento);
        AlertaUtil.alerta("Equipamento atualizado com sucesso!");

      }
      load();
      page = "list";
    }
    return page;
  }

  public boolean isValido(Equipamento e) {
    boolean valido = true;
    List<Equipamento> equipamentos = genericoJPA.buscarTodos("mac", e.getMac(), Equipamento.class);
    if (!equipamentos.isEmpty() && equipamentos.get(0).getId() != e.getId()) {
      AlertaUtil.alerta("MAC já Cadastrado", AlertaUtil.ERROR);
      valido = false;
    }
    return valido;
  }

  public String remover() {
    genericoJPA.remover(equipamento);
    load();
    AlertaUtil.alerta("Equipamento removido com sucesso!");
    return "list";
  }

  public String getEquipamentoId() {
    return equipamentoId;
  }

  public void setEquipamentoId(String equipamentoId) {
    this.equipamentoId = equipamentoId;
  }

  public Equipamento getEquipamento() {
    return equipamento;
  }

  public void setEquipamento(Equipamento equipamento) {
    this.equipamento = equipamento;
  }

}
