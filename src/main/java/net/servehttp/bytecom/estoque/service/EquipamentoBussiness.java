package net.servehttp.bytecom.estoque.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.estoque.jpa.EquipamentoJPA;
import net.servehttp.bytecom.estoque.jpa.entity.Equipamento;
import net.servehttp.bytecom.estoque.jpa.entity.StatusEquipamento;
import net.servehttp.bytecom.estoque.jpa.entity.TipoEquipamento;

public class EquipamentoBussiness implements Serializable {

  private static final long serialVersionUID = 8705835474790847188L;
  @Inject
  private EquipamentoJPA equipamentoJPA;

  public void remover(Equipamento e) {
    equipamentoJPA.remover(e);
  }

  public Equipamento buscarPorId(int id) {
    return equipamentoJPA.buscarPorId(Equipamento.class, id);
  }

  public List<Equipamento> buscarEquipamentosInstalacaoNaoUtilizados() {
    return equipamentoJPA.buscarEquipamentosNaoUtilizados(TipoEquipamento.INSTALACAO, StatusEquipamento.OK);
  }

  public List<Equipamento> buscarEquipamentosWifiNaoUtilizados() {
    return equipamentoJPA.buscarEquipamentosNaoUtilizados(TipoEquipamento.WIFI, StatusEquipamento.OK);
  }

}
