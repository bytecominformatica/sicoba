package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.EquipamentoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Equipamento;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusEquipamento;
import net.servehttp.bytecom.persistence.entity.cadastro.TipoEquipamento;

public class EquipamentoBussiness extends genericoBusiness implements Serializable {

  private static final long serialVersionUID = 8705835474790847188L;
  @Inject
  private EquipamentoJPA equipamentoJPA;

  public void remover(Equipamento e) {
    equipamentoJPA.remover(e);
  }

  public Equipamento findById(int id) {
    return genericoJPA.findById(Equipamento.class, id);
  }

  public List<Equipamento> buscarEquipamentosInstalacaoNaoUtilizados() {
    return equipamentoJPA.buscarEquipamentosNaoUtilizados(TipoEquipamento.INSTALACAO, StatusEquipamento.OK);
  }

  public List<Equipamento> buscarEquipamentosWifiNaoUtilizados() {
    return equipamentoJPA.buscarEquipamentosNaoUtilizados(TipoEquipamento.WIFI, StatusEquipamento.OK);
  }

}
