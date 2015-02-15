package net.servehttp.bytecom.comercial.controller;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.administrador.controller.ServidorController;
import net.servehttp.bytecom.comercial.jpa.entity.Acesso;
import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.comercial.jpa.entity.Contrato;
import net.servehttp.bytecom.comercial.service.AcessoBussiness;
import net.servehttp.bytecom.comercial.service.ClientBussiness;
import net.servehttp.bytecom.estoque.jpa.entity.Equipamento;
import net.servehttp.bytecom.util.web.AlertaUtil;

/**
 * 
 * @author clairton
 * 
 */
@Named
@ViewScoped
public class AcessoController implements Serializable {

  private static final long serialVersionUID = 7972159878826621995L;
  private static final String GATEWAY = "10.10.0.1";
  private static final String MASK = "255.255.0.0";
  private Cliente cliente;
  private int clienteId;
  @Inject
  private ServidorController servidorController;
  @Inject
  private AcessoBussiness acessoBusiness;
  @Inject
  private ClientBussiness clientBusiness;

  public void load() {
    if (clienteId > 0 && cliente == null) {
      cliente = clientBusiness.buscarPorId(clienteId);
    }
    if ((cliente.getAcesso()) == null) {
      getNovoAcesso();
    }
  }

  private void getNovoAcesso() {
    cliente.setAcesso(new Acesso());
    cliente.getAcesso().setCliente(cliente);

    String ipLivre = acessoBusiness.getIpLivre();
    cliente.getAcesso().setIp(ipLivre);
    cliente.getAcesso().setMascara(MASK);
    cliente.getAcesso().setGateway(GATEWAY);
    Contrato c = cliente.getContrato();
    if (c != null) {
      Equipamento e = c.getEquipamento();
      if (e != null) {
        cliente.getAcesso().setMac(e.getMac());
      }
    }
  }

  public void salvar() {
    if (isDisponivel(cliente.getAcesso())) {
      if (cliente.getAcesso().getId() == 0) {
        acessoBusiness.salvar(cliente.getAcesso());
        AlertaUtil.info("Acesso adicionado com sucesso!");
      } else {
        cliente.setUpdatedAt(LocalDateTime.now());
        acessoBusiness.atualizar(cliente);
        AlertaUtil.info("Acesso atualizado com sucesso!");
      }
      servidorController.atualizarAcesso();
      load();
    }
  }

  public void remover() {
    acessoBusiness.remover(cliente.getAcesso());
    cliente.setAcesso(null);
    load();
    AlertaUtil.info("Acesso removido com sucesso!");
  }

  private boolean isDisponivel(Acesso a) {
    boolean disponivel = true;
    if (a == null) {
      disponivel = false;
      AlertaUtil.error("Acesso Null");
    } else {

      if (!acessoBusiness.isIpDisponivel(a)) {
        AlertaUtil.error("IP já Cadastrado");
        disponivel = false;
      } else {

        if (!acessoBusiness.isMacDisponivel(a)) {
          AlertaUtil.error("MAC já Cadastrado");
          disponivel = false;
        }
      }
    }
    return disponivel;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public int getClienteId() {
    return clienteId;
  }

  public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
  }

}
