package net.servehttp.bytecom.controller.comercial;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.controller.extra.GenericoController;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Bairro;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cidade;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.pojo.comercial.EnderecoPojo;
import net.servehttp.bytecom.service.comercial.AddressBussiness;
import net.servehttp.bytecom.service.comercial.ClienteBussiness;
import net.servehttp.bytecom.service.provedor.MikrotikPPP;
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
  private ClienteBussiness clientBussiness;
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
          cliente.getStatus().atualizarConexao(cliente, mikrotikPPP);
          clientBussiness.atualizar(cliente);
          AlertaUtil.info("Cliente atualizado com sucesso!");
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
