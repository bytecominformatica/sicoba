package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.business.ClientBussiness;
import net.servehttp.bytecom.business.ContratoBusiness;
import net.servehttp.bytecom.business.EquipamentoBussiness;
import net.servehttp.bytecom.business.PlanoBussiness;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.Contrato;
import net.servehttp.bytecom.persistence.entity.cadastro.Equipamento;
import net.servehttp.bytecom.persistence.entity.cadastro.Plano;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.Util;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class ContratoController implements Serializable {

  private static final long serialVersionUID = -5226446405193705169L;
  private List<Contrato> listContratos;
  private List<Plano> listPlanos;
  private List<Equipamento> listEquipamentos;
  private List<Equipamento> listEquipamentosWifi;

  private Cliente cliente;
  private int clienteId;
  private int planoId;
  private int equipamentoId;
  private int equipamentoWifiId;
  @Inject
  private Util util;
  @Inject
  private PlanoBussiness planoBusiness;
  @Inject
  private EquipamentoBussiness equipamentoBusiness;
  @Inject
  private ClientBussiness clientBusiness;
  @Inject
  private ContratoBusiness contratoBusiness;

  @PostConstruct
  public void load() {
    listPlanos = planoBusiness.findAll();
    listEquipamentos = equipamentoBusiness.buscarEquipamentosInstalacaoNaoUtilizados();
    listEquipamentosWifi = equipamentoBusiness.buscarEquipamentosWifiNaoUtilizados();
    getParameters();
  }

  private void getParameters() {
    String clienteId = util.getParameters("clienteId");
    if (clienteId != null && !clienteId.isEmpty()) {
      setCliente(clientBusiness.findById(Integer.parseInt(clienteId)));
      if (getCliente().getContrato() == null) {
        Contrato c = new Contrato();
        c.setCliente(getCliente());
        c.setDataInstalacao(new Date());
        getCliente().setContrato(c);
      }

      if (getCliente().getContrato().getPlano() != null) {
        planoId = getCliente().getContrato().getPlano().getId();
      }

      Equipamento e = getCliente().getContrato().getEquipamento();
      if (e != null && !listEquipamentos.contains(e)) {
        equipamentoId = e.getId();
        listEquipamentos.add(e);
      }

      e = getCliente().getContrato().getEquipamentoWifi();
      if (e != null && !listEquipamentosWifi.contains(e)) {
        equipamentoWifiId = e.getId();
        listEquipamentosWifi.add(e);
      }
    }
  }

  public List<Equipamento> getListEquipamentosWifi() {
    return listEquipamentosWifi;
  }

  public void setListEquipamentosWifi(List<Equipamento> listEquipamentosWifi) {
    this.listEquipamentosWifi = listEquipamentosWifi;
  }

  public int getPlanoId() {
    return planoId;
  }

  public void setPlanoId(int planoId) {
    this.planoId = planoId;
    if (planoId > 0) {
      getCliente().getContrato().setPlano(planoBusiness.findById(planoId));
    }
  }

  public int getEquipamentoId() {
    return equipamentoId;
  }

  public void setEquipamentoId(int equipamentoId) {
    this.equipamentoId = equipamentoId;
    getCliente().getContrato().setEquipamento(buscaNaListaEquipamento(equipamentoId));
  }

  public int getEquipamentoWifiId() {
    return equipamentoWifiId;
  }

  public void setEquipamentoWifiId(int equipamentoWifiId) {
    this.equipamentoWifiId = equipamentoWifiId;
    getCliente().getContrato().setEquipamentoWifi(buscaNaListaWifi(equipamentoWifiId));
  }

  private Equipamento buscaNaListaWifi(int id) {
    for (Equipamento e : listEquipamentosWifi) {
      if (e.getId() == id) {
        return e;
      }
    }
    return null;
  }

  private Equipamento buscaNaListaEquipamento(int id) {
    for (Equipamento e : listEquipamentos) {
      if (e.getId() == id) {
        return e;
      }
    }
    return null;
  }

  public List<Equipamento> getListEquipamentos() {
    return listEquipamentos;
  }

  public void setListEquipamentos(List<Equipamento> listEquipamentos) {
    this.listEquipamentos = listEquipamentos;
  }

  public List<Plano> getListPlanos() {
    return listPlanos;
  }

  public void setListPlanos(List<Plano> listPlanos) {
    this.listPlanos = listPlanos;
  }

  public List<Contrato> getListContratos() {
    return listContratos;
  }

  public void setListContratos(List<Contrato> listContratos) {
    this.listContratos = listContratos;
  }

  public String salvar() {
    String page = null;
    if (cliente.getContrato().getId() == 0) {
      contratoBusiness.salvar(cliente.getContrato());
      load();
      AlertaUtil.info("Contrato adicionado com sucesso!");
      page = "edit";
    } else {
      contratoBusiness.atualizar(cliente.getContrato());
      load();
      AlertaUtil.info("Contrato atualizado com sucesso!");
      page = "edit";
    }
    return page;
  }

  public void remover() {
    contratoBusiness.remover(cliente.getContrato());
    cliente.setContrato(new Contrato());
    load();
    AlertaUtil.info("Contrato removido com sucesso!");
  }

  public int getClienteId() {
    return clienteId;
  }

  public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

}
