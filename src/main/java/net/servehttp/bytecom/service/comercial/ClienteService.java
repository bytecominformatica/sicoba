package net.servehttp.bytecom.service.comercial;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.jpa.comercial.ClienteJPA;
import net.servehttp.bytecom.persistence.jpa.comercial.ConexaoJPA;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Conexao;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.StatusCliente;
import net.servehttp.bytecom.persistence.jpa.extra.GenericoJPA;
import net.servehttp.bytecom.service.provedor.IConnectionControl;
import net.servehttp.bytecom.util.MensagemException;

public class ClienteService implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;

  @Inject
  private ConexaoJPA conexaoJPA;
  @Inject
  private IConnectionControl connectionControl;
  @Inject
  private ClienteJPA clienteJPA;
  @Inject
  private GenericoJPA jpa;

  public List<Cliente> buscaUltimosClientesAlterados() {
    return clienteJPA.buscaUltimosClientesAlterados();
  }

  public Cliente buscarPorId(int id) {
    return jpa.buscarPorId(Cliente.class, id);
  }

  public List<Cliente> buscarTodosClientePorNomeIp(String nome, String ip, StatusCliente status) {
    return clienteJPA.buscarTodosClientePorNomeIp(nome, ip, status);
  }

  public boolean rgAvaliable(Cliente c) {
    List<Cliente> clientes = jpa.buscarTodos("rg", c.getRg(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }

  public boolean cpfCnpjAvaliable(Cliente c) {
    List<Cliente> clientes = jpa.buscarTodos("cpfCnpj", c.getCpfCnpj(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }

  public boolean emailAvaliable(Cliente c) {
    List<Cliente> clientes = jpa.buscarTodos("email", c.getEmail(), Cliente.class);
    return clientes.isEmpty() || clientes.get(0).getId() == c.getId();
  }

  public void remover(Cliente cliente) {
    jpa.remover(cliente);
  }

  public void salvar(Cliente cliente) throws Exception {
    if (isAvaliable(cliente)) {
      cliente.getStatus().atualizarConexao(cliente, connectionControl);
      jpa.salvar(cliente);
    }
  }

  public boolean isAvaliable(Cliente cliente) throws MensagemException {
    if (!rgAvaliable(cliente)) {
      throw new MensagemException("RG já Cadastrado");
    } else if (!cpfCnpjAvaliable(cliente)) {
      throw new MensagemException("CPF já Cadastrado");
    } else if (!emailAvaliable(cliente)) {
      throw new MensagemException("E-Mail já Cadastrado");
    }
    return true;
  }

  public void atualizarTodasConexoes() throws Exception {
    List<Conexao> list = jpa.buscarTodos(Conexao.class);

    connectionControl.setAutoCloseable(true);
    try (IConnectionControl cc = connectionControl) {
      for (Conexao c : list) {
        if (c.getIp() == null || c.getIp().isEmpty()) {
          c.setIp(conexaoJPA.getIpLivre());
        }

        c.getCliente().getStatus().atualizarConexao(c.getCliente(), cc);
        jpa.salvar(c);
      }
    }
    connectionControl.setAutoCloseable(false);

  }

}
