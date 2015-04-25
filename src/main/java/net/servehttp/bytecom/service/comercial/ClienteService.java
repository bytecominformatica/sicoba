package net.servehttp.bytecom.service.comercial;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.jpa.comercial.ClienteJPA;
import net.servehttp.bytecom.persistence.jpa.comercial.ConexaoJPA;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Conexao;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.StatusCliente;
import net.servehttp.bytecom.service.provedor.IConnectionControl;
import net.servehttp.bytecom.util.web.AlertaUtil;

public class ClienteService implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;

  @Inject
  private ConexaoJPA conexaoJPA;
  @Inject
  private IConnectionControl connectionControl;
  @Inject
  private ClienteJPA clienteJPA;

  public List<Cliente> buscaUltimosClientesAlterados() {
    return clienteJPA.buscaUltimosClientesAlterados();
  }

  public Cliente buscarPorId(int id) {
    return clienteJPA.buscarPorId(Cliente.class, id);
  }

  public List<Cliente> buscarTodosClientePorNomeIp(String nome, String ip, StatusCliente status) {
    return clienteJPA.buscarTodosClientePorNomeIp(nome, ip, status);
  }

  public boolean rgAvaliable(Cliente c) {
    List<Cliente> clientes = clienteJPA.buscarTodos("rg", c.getRg(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }

  public boolean cpfCnpjAvaliable(Cliente c) {
    List<Cliente> clientes = clienteJPA.buscarTodos("cpfCnpj", c.getCpfCnpj(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }

  public boolean emailAvaliable(Cliente c) {
    List<Cliente> clientes = clienteJPA.buscarTodos("email", c.getEmail(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }

  public <T> T salvar(T t) {
    return clienteJPA.salvar(t);
  }

  public <T> T atualizar(T t) {
    return clienteJPA.atualizar(t);
  }

  public void remover(Cliente cliente) {
    clienteJPA.remover(cliente);
  }

  public void salvar(Cliente cliente) throws Exception {
    String irregularidade = getIrregularidade(cliente);
    if (cliente.getId() == 0) {
      cliente.setCreatedAt(LocalDateTime.now());
      clienteJPA.salvar(cliente);
    } else {
      cliente.getStatus().atualizarConexao(cliente, connectionControl);
      clienteJPA.atualizar(cliente);
    }
  }

  public String getIrregularidade(Cliente cliente) {
    String erro = null;
    if (!rgAvaliable(cliente)) {
      erro = "RG já Cadastrado";
    } else if (!cpfCnpjAvaliable(cliente)) {
      erro = "CPF já Cadastrado";
    } else if (!emailAvaliable(cliente)) {
      erro = "E-Mail já Cadastrado";
    }
    return erro;
  }

  public void atualizarTodasConexoes() throws Exception {
    List<Conexao> list = conexaoJPA.buscarTodos(Conexao.class);

    for (Conexao c : list) {
      if (c.getIp() == null || c.getIp().isEmpty()) {
        c.setIp(conexaoJPA.getIpLivre());
      }
      connectionControl.save(c.getMikrotik(), c);
      conexaoJPA.atualizar(c);
    }

  }

}
