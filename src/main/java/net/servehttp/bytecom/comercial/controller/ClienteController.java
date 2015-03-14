package net.servehttp.bytecom.comercial.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.administrador.controller.ServidorController;
import net.servehttp.bytecom.comercial.jpa.entity.Bairro;
import net.servehttp.bytecom.comercial.jpa.entity.Cidade;
import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.comercial.jpa.entity.StatusCliente;
import net.servehttp.bytecom.comercial.pojo.EnderecoPojo;
import net.servehttp.bytecom.comercial.service.AddressBussiness;
import net.servehttp.bytecom.comercial.service.ClientBussiness;
import net.servehttp.bytecom.extra.controller.GenericoController;
import net.servehttp.bytecom.provedor.service.mikrotik.MikrotikPPP;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

/**
 * 
 * @author clairtonluz
 */
@Named
@ViewScoped
public class ClienteController extends GenericoController implements Serializable {

  private static final long serialVersionUID = 8827281306259995250L;
  private Cliente cliente = new Cliente();
  private List<Cidade> listCidades;
  private List<Bairro> listBairros;
  private Cidade cidade;

  @Inject
  private ServidorController servidorController;
  @Inject
  private ClientBussiness clientBussiness;
  @Inject
  private AddressBussiness addressBussiness;
  @Inject
  private MikrotikPPP mikrotikPPP;

  @PostConstruct
  public void load() {
    setListCidades(addressBussiness.findCities());
    getParameters();
  }

  private void getParameters() {
    String clienteId = WebUtil.getParameters("id");
    if (clienteId != null && !clienteId.isEmpty()) {
      cliente = clientBussiness.buscarPorId(Integer.parseInt(clienteId));
      selecionaCidade();
      atualizaBairros();
    }
  }

  private void selecionaCidade() {
    if (cliente.getEndereco().getBairro() != null) {
      cidade = cliente.getEndereco().getBairro().getCidade();
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
    try {
      if (isClienteValido(cliente)) {
        if (cliente.getId() == 0) {
          cliente.setCreatedAt(LocalDateTime.now());
          clientBussiness.salvar(cliente);
          AlertaUtil.info("Cliente adicionado com sucesso!");
        } else {
          if (cliente.getStatus().equals(StatusCliente.CANCELADO)) {
            cliente.setAcesso(null);
          }
          clientBussiness.atualizar(cliente);
          AlertaUtil.info("Cliente atualizado com sucesso!");
        }
        if (cliente.getAcesso() != null) {
          servidorController.atualizarAcesso();
        }
        if (cliente.getConexao() != null) {
          mikrotikPPP.salvarSecret(cliente.getConexao());
        }
      }
    } catch (Exception e) {
      log(e);
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

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Cidade getCidade() {
    return cidade;
  }

  public void setCidade(Cidade cidade) {
    this.cidade = cidade;
  }

}
