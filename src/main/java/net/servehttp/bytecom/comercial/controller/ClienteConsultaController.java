package net.servehttp.bytecom.comercial.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.comercial.jpa.entity.StatusCliente;
import net.servehttp.bytecom.comercial.service.ClienteBussiness;
import net.servehttp.bytecom.extra.controller.GenericoController;

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
  private ClienteBussiness business;

  @PostConstruct
  public void load() {
    listClientes = business.buscaUltimosClientesAlterados();
  }
  
  public void atualizarTodasConexoes() {
//    business.atualizarTodasConexoes();
  }

  public StatusCliente[] getListStatus() {
    return StatusCliente.values();
  }

  public void consultar() {
    listClientes = business.buscarTodosClientePorNomeIp(nome, ip, status);
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
