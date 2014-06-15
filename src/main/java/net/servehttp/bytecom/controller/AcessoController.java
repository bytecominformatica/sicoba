package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Acesso;
import net.servehttp.bytecom.persistence.entity.Cliente;
import net.servehttp.bytecom.persistence.entity.Contrato;
import net.servehttp.bytecom.persistence.entity.Equipamento;
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
  private Cliente cliente;
  private int clienteId;
  @Inject
  GenericoJPA genericoJPA;


  public void load() {
    System.out.println("Cliente = " + clienteId);
    if (clienteId > 0) {
      cliente = genericoJPA.buscarPorId(Cliente.class, clienteId);
      if ((cliente.getAcesso()) == null) {
        System.out.println("Acesso null");
        cliente.setAcesso(new Acesso());
        cliente.getAcesso().setCliente(cliente);
        cliente.getAcesso().setIp("192.168.33.2");
        cliente.getAcesso().setMascara("255.255.255.0");
        cliente.getAcesso().setGateway("192.168.33.1");
        cliente.getAcesso().setStatus(Acesso.ATIVO);
        System.out.println("ACESSO = " + cliente.getAcesso());
        Contrato c = cliente.getContrato();
        if (c != null) {
          System.out.println("contrato not null");
          Equipamento e = c.getEquipamento();
          if (e != null) {
            System.out.println("equipamento not null");
            cliente.getAcesso().setMac(e.getMac());
          }
        }
      }
    }
  }

  public String salvar() {
    String page = null;

    System.out.println("Salvar cliente = " + cliente);
    System.out.println("Salvar acess0 = " + cliente.getAcesso());
    if (isDisponivel(cliente.getAcesso())) {

      System.out.println("Salvar cliente = " + cliente);
      System.out.println("Salvar acess0 = " + cliente.getAcesso());
      if (cliente.getAcesso().getId() == 0) {
        genericoJPA.salvar(cliente.getAcesso());
        AlertaUtil.alerta("Acesso adicionado com sucesso!");
      } else {
        genericoJPA.atualizar(cliente.getAcesso());
        AlertaUtil.alerta("Acesso atualizado com sucesso!");
      }
      load();
      page = "edit";
    }
    return page;
  }

  public void remover() {
    genericoJPA.remover(cliente.getAcesso());
    load();
    AlertaUtil.alerta("Acesso removido com sucesso!");
  }

  private boolean isDisponivel(Acesso a) {
    boolean disponivel = true;
    if (a == null) {
      disponivel = false;
      AlertaUtil.alerta("Acesso Null", AlertaUtil.ERROR);
    } else {

      List<Acesso> acessos = genericoJPA.buscarTodos("ip", a.getIp(), Acesso.class);
      if (!acessos.isEmpty() && acessos.get(0).getId() != a.getId()) {
        AlertaUtil.alerta("IP já Cadastrado", AlertaUtil.ERROR);
        disponivel = false;
      } else {

        acessos = genericoJPA.buscarTodos("mac", a.getMac(), Acesso.class);
        if (!acessos.isEmpty() && acessos.get(0).getId() != a.getId()) {
          AlertaUtil.alerta("MAC já Cadastrado", AlertaUtil.ERROR);
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
