package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.ClienteJPA;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Bairro;
import net.servehttp.bytecom.persistence.entity.Cidade;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.pojo.EnderecoPojo;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.EnderecoUtil;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class ClienteController implements Serializable {


  private static final long serialVersionUID = 8827281306259995250L;
  private List<Cliente> listClientes;
  private Cliente cliente = new Cliente();
  private List<Cidade> listCidades;
  private List<Bairro> listBairros;
  private int cidadeId;
  private int bairroId;
  private String page;
  private String pesquisa;

  @Inject
  private ClienteJPA clienteJPA;
  @Inject
  private GenericoJPA genericoJPA;


  @Inject
  private EnderecoUtil enderecoUtil;
  @Inject
  private Util util;

  public ClienteController() {}

  @PostConstruct
  public void load() {
    listClientes = clienteJPA.buscaUltimosClientesAlterados();
    setListCidades(genericoJPA.buscarTodos(Cidade.class));
    getParameters();
  }

  private void getParameters() {
    String clienteId = util.getParameters("id");
    if (clienteId != null && !clienteId.isEmpty()) {
      cliente = genericoJPA.findById(Cliente.class, Integer.parseInt(clienteId));
      cidadeId = cliente.getEndereco().getBairro().getCidade().getId();
      bairroId = cliente.getEndereco().getBairro().getId();
      atualizaBairros();
    }
  }

  public void consultar() {
    if (pesquisa != null && pesquisa.length() > 2) {
      listClientes = clienteJPA.buscaClientesPorNomeFoneEmail(pesquisa);
    } else {
      AlertaUtil.alerta("pesquisa por nome tem que possuir pelo menos 3 caracteres.",
          AlertaUtil.ERROR);
    }
  }

  public void atualizaBairros() {
    for (Cidade c : listCidades) {
      if (c.getId() == cidadeId) {
        listBairros = c.getBairros();
      }
    }
  }

  public String salvar() {
    page = null;
    if (isClienteValido(cliente)) {
      cliente.getEndereco().setBairro(genericoJPA.findById(Bairro.class, bairroId));
      if (cliente.getId() == 0) {
        genericoJPA.salvar(cliente);
        AlertaUtil.alerta("Cliente adicionado com sucesso!");
      } else {
        genericoJPA.atualizar(cliente);
        AlertaUtil.alerta("Cliente atualizado com sucesso!");
      }
      page = "list";
    }
    return page;
  }


  private boolean isClienteValido(Cliente cliente) {
    boolean valido = true;

    removeCamposVazios(cliente);

    List<Cliente> clientes = genericoJPA.buscarTodos("rg", cliente.getRg(), Cliente.class);
    if (!clientes.isEmpty() && clientes.get(0).getId() != cliente.getId()) {
      AlertaUtil.alerta("RG já Cadastrado", AlertaUtil.ERROR);
      valido = false;
    } else {
      clientes = genericoJPA.buscarTodos("cpfCnpj", cliente.getCpfCnpj(), Cliente.class);
      if (!clientes.isEmpty() && clientes.get(0).getId() != cliente.getId()) {
        AlertaUtil.alerta("CPF já Cadastrado", AlertaUtil.ERROR);
        valido = false;
      } else {
        clientes = genericoJPA.buscarTodos("email", cliente.getEmail(), Cliente.class);
        if (!clientes.isEmpty() && clientes.get(0).getId() != cliente.getId()) {
          AlertaUtil.alerta("E-Mail já Cadastrado", AlertaUtil.ERROR);
          valido = false;
        }
      }
    }

    return valido;
  }

  private void removeCamposVazios(Cliente c) {
    if (c.getRg() != null && c.getRg().isEmpty()) {
      c.setRg(null);
    }
    if (c.getCpfCnpj() != null && c.getCpfCnpj().isEmpty()) {
      c.setCpfCnpj(null);
    }
    if (c.getEmail() != null && c.getEmail().isEmpty()) {
      c.setEmail(null);
    }
  }

  public String remover() {
    page = null;
    if (cliente.getAcesso() != null) {
      AlertaUtil.alerta("Cliente não pode ser removido pois possui acesso cadastrado",
          AlertaUtil.WARN);
    } else if (cliente.getContrato() != null) {
      AlertaUtil.alerta("O cliente não pode ser removido pois possui contrato", AlertaUtil.WARN);
    } else {
      genericoJPA.remover(cliente);
      AlertaUtil.alerta("Cliente removido com sucesso!");
      page = "list";
    }
    return page;
  }

  public void buscarEndereco() {
    cidadeId = bairroId = 0;
    cliente.getEndereco().setLogradouro(null);
    EnderecoPojo ep = enderecoUtil.getEndereco(cliente.getEndereco().getCep());
    cliente.getEndereco().setBairro(enderecoUtil.getBairro(ep));
    listCidades = genericoJPA.buscarTodos(Cidade.class);

    if (cliente.getEndereco().getBairro() != null) {
      cidadeId = cliente.getEndereco().getBairro().getCidade().getId();
      atualizaBairros();
      bairroId = cliente.getEndereco().getBairro().getId();
      cliente.getEndereco().setLogradouro(ep.getLogradouro());
    }

  }

  public List<Cidade> getListCidades() {
    return listCidades;
  }

  public void setListCidades(List<Cidade> listCidades) {
    this.listCidades = listCidades;
  }

  public List<Bairro> getListBairros() {
    return listBairros;
  }

  public void setListBairros(List<Bairro> listBairros) {
    this.listBairros = listBairros;
  }

  public int getCidadeId() {
    return cidadeId;
  }

  public void setCidadeId(int cidadeId) {
    this.cidadeId = cidadeId;
  }

  public int getBairroId() {
    return bairroId;
  }

  public void setBairroId(int bairroId) {
    this.bairroId = bairroId;
  }

  public List<Cliente> getListClientes() {
    return listClientes;
  }

  public void setListClientes(List<Cliente> listClientes) {
    this.listClientes = listClientes;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public String getPesquisa() {
    return pesquisa;
  }

  public void setPesquisa(String pesquisa) {
    this.pesquisa = pesquisa;
  }

}
