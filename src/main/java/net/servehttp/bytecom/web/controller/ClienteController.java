package net.servehttp.bytecom.web.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.business.AddressBussiness;
import net.servehttp.bytecom.business.ClientBussiness;
import net.servehttp.bytecom.persistence.entity.Bairro;
import net.servehttp.bytecom.persistence.entity.Cidade;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.pojo.EnderecoPojo;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.Util;

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
  private Cidade cidade;
  private String page;
  private String pesquisa;

  @Inject
  private ServidorController servidorController;
  @Inject
  private ClientBussiness clientBussiness;
  @Inject
  private AddressBussiness addressBussiness;
  @Inject
  private Util util;

  @PostConstruct
  public void load() {
    listClientes = clientBussiness.buscaUltimosClientesAlterados();
    setListCidades(addressBussiness.findCities());
    getParameters();
  }

  private void getParameters() {
    String clienteId = util.getParameters("id");
    if (clienteId != null && !clienteId.isEmpty()) {
      cliente = clientBussiness.findById(Integer.parseInt(clienteId));
      selecionaCidade();
      atualizaBairros();
    }
  }

  private void selecionaCidade() {
    if (cliente.getEndereco().getBairro() != null) {
      cidade = cliente.getEndereco().getBairro().getCidade();
    }
  }

  public void consultar() {
    if (pesquisa != null && pesquisa.length() > 2) {
      listClientes = clientBussiness.findClientByNamePhoneEmail(pesquisa);
    } else {
      AlertaUtil.error("pesquisa por nome tem que possuir pelo menos 3 caracteres.");
    }
  }

  public void atualizaBairros() {
    if (cidade != null) {
      listBairros = cidade.getBairros();
    } else if (listBairros != null) {
      listBairros.clear();
    }
  }

  public void salvar() {
    if (isClienteValido(cliente)) {
      if (cliente.getId() == 0) {
        cliente.setCreatedAt(Calendar.getInstance());
        clientBussiness.salvar(cliente);
        AlertaUtil.info("Cliente adicionado com sucesso!");
      } else {
        clientBussiness.atualizar(cliente);
        AlertaUtil.info("Cliente atualizado com sucesso!");
      }
      if (cliente.getAcesso() != null) {
        servidorController.atualizarAcesso();
      }
    }
  }

  private boolean isClienteValido(Cliente cliente) {
    boolean valido = true;
    if (!clientBussiness.rgAvaliable(cliente)) {
      AlertaUtil.error("RG já Cadastrado");
      valido = false;
    } else if (!clientBussiness.cpfCnpjAvaliable(cliente)) {
      AlertaUtil.error("CPF já Cadastrado");
      valido = false;
    } else if (!clientBussiness.emailAvaliable(cliente)) {
      AlertaUtil.error("E-Mail já Cadastrado");
      valido = false;
    }
    return valido;
  }

  public String remover() {
    page = null;
    if (cliente.getAcesso() != null) {
      AlertaUtil.warn("Cliente não pode ser removido pois possui acesso cadastrado");
    } else if (cliente.getContrato() != null) {
      AlertaUtil.error("O cliente não pode ser removido pois possui contrato");
    } else {
      clientBussiness.remover(cliente);
      selecionaCidade();
      AlertaUtil.info("Cliente removido com sucesso!");
      page = "list";
    }
    return page;
  }

  public void buscarEndereco() {
    cidade = null;
    cliente.getEndereco().setLogradouro(null);
    EnderecoPojo ep = addressBussiness.findAddressByCep(cliente.getEndereco().getCep());
    cliente.getEndereco().setBairro(addressBussiness.getNeighborhood(ep));
    listCidades = addressBussiness.findCities();

    if (cliente.getEndereco().getBairro() != null) {
      cidade = cliente.getEndereco().getBairro().getCidade();
      atualizaBairros();
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

  public Cidade getCidade() {
    return cidade;
  }

  public void setCidade(Cidade cidade) {
    this.cidade = cidade;
  }

}
