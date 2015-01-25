package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.ClienteJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusCliente;

public class ClientBussiness extends genericoBusiness implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;

  @Inject
  private ClienteJPA clienteJPA;

  public List<Cliente> buscaUltimosClientesAlterados() {
    return clienteJPA.buscaUltimosClientesAlterados();
  }

  public Cliente findById(int id) {
    return genericoJPA.findById(Cliente.class, id);
  }

  public List<Cliente> buscarTodosClientePorNomeIp(String nome, String ip, StatusCliente status) {
    return clienteJPA.buscarTodosClientePorNomeIp(nome, ip, status);
  }
  
  public boolean rgAvaliable(Cliente c){
    List<Cliente> clientes = genericoJPA.buscarTodos("rg", c.getRg(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }
  
  public boolean cpfCnpjAvaliable(Cliente c){
    List<Cliente> clientes = genericoJPA.buscarTodos("cpfCnpj", c.getCpfCnpj(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }
  
  public boolean emailAvaliable(Cliente c){
    List<Cliente> clientes = genericoJPA.buscarTodos("email", c.getEmail(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }
}
