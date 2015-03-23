package net.servehttp.bytecom.comercial.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.comercial.jpa.entity.Conexao;
import net.servehttp.bytecom.comercial.jpa.entity.StatusCliente;
import net.servehttp.bytecom.comercial.service.ClientBussiness;
import net.servehttp.bytecom.extra.controller.GenericoController;
import net.servehttp.bytecom.provedor.service.mikrotik.MikrotikPPP;

/**
 * 
 * @author clairtonluz
 */
@Named
@ViewScoped
public class ClienteConsultaController extends GenericoController implements Serializable {

  private static final long serialVersionUID = 8827281306259995250L;
  private List<Cliente> listClientes;
  private String nome;
  private String ip;
  private StatusCliente status;

  @Inject
  private MikrotikPPP mikrotikPPP;
  @Inject
  private ClientBussiness clientBussiness;

  @PostConstruct
  public void load() {
    listClientes = clientBussiness.buscaUltimosClientesAlterados();
  }

  public String getIp(Conexao c) {
    try {
      return mikrotikPPP.getIp(c);
    } catch (Exception e) {
      log(e);
    }
    return null;
  }

  public StatusCliente[] getListStatus() {
    return StatusCliente.values();
  }

  public void consultar() {
    listClientes = clientBussiness.buscarTodosClientePorNomeIp(nome, ip, status);
  }

  public List<Cliente> getListClientes() {
    return listClientes;
  }

  public void setListClientes(List<Cliente> listClientes) {
    this.listClientes = listClientes;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public StatusCliente getStatus() {
    return status;
  }

  public void setStatus(StatusCliente status) {
    this.status = status;
  }

}
