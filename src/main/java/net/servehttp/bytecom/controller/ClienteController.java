package net.servehttp.bytecom.controller;

import java.io.Serializable;
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
  private int cidadeId;
  private int bairroId;
  private String page;
  private String pesquisa;


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
      cidadeId = cliente.getEndereco().getBairro().getCidade().getId();
      bairroId = cliente.getEndereco().getBairro().getId();
      atualizaBairros();
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
    for (Cidade c : listCidades) {
      if (c.getId() == cidadeId) {
        listBairros = c.getBairros();
      }
    }
  }

  public void salvar() {
    if (isClienteValido(cliente)) {
      cliente.getEndereco().setBairro(addressBussiness.findById(bairroId));
      if (cliente.getId() == 0) {
        clientBussiness.salvar(cliente);
        AlertaUtil.info("Cliente adicionado com sucesso!");
      } else {
        clientBussiness.atualizar(cliente);
        AlertaUtil.info("Cliente atualizado com sucesso!");
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
      AlertaUtil.info("Cliente removido com sucesso!");
      page = "list";
    }
    return page;
  }

  public void buscarEndereco() {
    cidadeId = bairroId = 0;
    cliente.getEndereco().setLogradouro(null);
    EnderecoPojo ep = addressBussiness.findAddressByCep(cliente.getEndereco().getCep());
    cliente.getEndereco().setBairro(addressBussiness.getNeighborhood(ep));
    listCidades = addressBussiness.findCities();

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
