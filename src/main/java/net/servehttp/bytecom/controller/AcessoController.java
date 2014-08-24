package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.business.AcessoBussiness;
import net.servehttp.bytecom.business.ClientBussiness;
import net.servehttp.bytecom.persistence.entity.cadastro.Acesso;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.Contrato;
import net.servehttp.bytecom.persistence.entity.cadastro.Equipamento;
import net.servehttp.bytecom.util.AlertaUtil;

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
      cliente = clientBusiness.findById(clienteId);
      if ((cliente.getAcesso()) == null) {
        cliente.setAcesso(new Acesso());
        cliente.getAcesso().setCliente(cliente);

        String ipLivre = acessoBusiness.getIpLivre();
        cliente.getAcesso().setIp(ipLivre);
        cliente.getAcesso().setMascara(MASK);
        cliente.getAcesso().setGateway(GATEWAY);
        cliente.getAcesso().setStatus(Acesso.ATIVO);
        Contrato c = cliente.getContrato();
        if (c != null) {
          Equipamento e = c.getEquipamento();
          if (e != null) {
            cliente.getAcesso().setMac(e.getMac());
          }
        }
      }
    }
  }

  public void salvar() {
    if (isDisponivel(cliente.getAcesso())) {
      if (cliente.getAcesso().getId() == 0) {
        acessoBusiness.salvar(cliente.getAcesso());
        AlertaUtil.info("Acesso adicionado com sucesso!");
      } else {
        cliente.setUpdatedAt(Calendar.getInstance());
        acessoBusiness.atualizar(cliente);
        AlertaUtil.info("Acesso atualizado com sucesso!");
      }
      servidorController.atualizarAcesso();
      load();
    }
  }

  public void remover() {
    acessoBusiness.remover(cliente.getAcesso());
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
