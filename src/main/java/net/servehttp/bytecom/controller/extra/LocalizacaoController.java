package net.servehttp.bytecom.controller.extra;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.extra.ClienteGeoReferencia;
import net.servehttp.bytecom.persistence.jpa.extra.ClienteGeoReferenciaJPA;
import net.servehttp.bytecom.pojo.extra.Location;
import net.servehttp.bytecom.service.comercial.ClienteService;
import net.servehttp.bytecom.util.extra.GoogleMaps;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

/**
 * 
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a> <br>
 * @author Felipe W. M. Martins <br>
 *         felipewmartins@gmail.com
 *
 */
@Named
@ViewScoped
public class LocalizacaoController extends GenericoController implements Serializable {

  private static final long serialVersionUID = -6695262369077987911L;

  private List<Cliente> listClientes;
  private int cidadeId;
  private int bairroId;
  private String clienteId;
  private Cliente cliente = new Cliente();

  @Inject
  private ClienteService clientService;
  @Inject
  private ClienteGeoReferenciaJPA clienteGeoReferenciaJPA;

  @PostConstruct
  public void load() {
    listClientes = clientService.buscaUltimosClientesAlterados();
    getParameters();
  }

  private void getParameters() {
    clienteId = WebUtil.getParameters("id");
    if (clienteId != null && !clienteId.isEmpty()) {
      cliente = clientService.buscarPorId(Integer.parseInt(clienteId));
      cidadeId = cliente.getEndereco().getBairro().getCidade().getId();
      bairroId = cliente.getEndereco().getBairro().getId();
    }
  }

  public void geocodificarTodos() {
    int i = 0;
    List<Cliente> clientes = jpa.buscarTodos(Cliente.class);
    for (Cliente c : clientes) {
      if (geocodificar(c)) {
        i++;
      }
    }
    AlertaUtil.info(String.format("Clientes referênciados: %d Clientes não referênciados: %s", i,
        clientes.size() - i));
  }

  public void geocodificar() {
    if (geocodificar(cliente)) {
      AlertaUtil.info("Gravado com sucesso");
    } else {
      AlertaUtil.warn("Localização não encontrada");
    }
  }

  public boolean geocodificar(Cliente cliente) {
    boolean sucesso = false;
    Location location = GoogleMaps.getLocation(cliente.getEndereco());
    if (location != null) {
      ClienteGeoReferencia clienteGeo = clienteGeoReferenciaJPA.buscarClienteGeoReferenciaPorCliente(cliente);
      if (clienteGeo == null) {
        clienteGeo = new ClienteGeoReferencia();
        clienteGeo.setCliente(cliente);
      }
      clienteGeo.setLatitude(location.getLat());
      clienteGeo.setLongitude(location.getLng());

      jpa.salvar(clienteGeo);
      sucesso = true;
    }
    return sucesso;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<Cliente> getListClientes() {
    return listClientes;
  }

  public void setListClientes(List<Cliente> listClientes) {
    this.listClientes = listClientes;
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



}
