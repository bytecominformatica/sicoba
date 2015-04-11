package net.servehttp.bytecom.controller.extra;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.extra.pojo.Location;
import net.servehttp.bytecom.extra.util.GoogleMaps;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.extra.ClienteGeoReferencia;
import net.servehttp.bytecom.persistence.jpa.extra.ClienteGeoReferenciaJPA;
import net.servehttp.bytecom.service.comercial.ClienteBussiness;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

/**
 * 
 * @author Felipe W. M. Martins <br>
 *         felipewmartins@gmail.com
 *
 */
@Named
@ViewScoped
public class LocalizacaoController implements Serializable {

  private static final long serialVersionUID = -6695262369077987911L;

  private List<Cliente> listClientes;
  private int cidadeId;
  private int bairroId;
  private String clienteId;
  private Cliente cliente = new Cliente();

  @Inject
  private ClienteBussiness clientBussiness;
  @Inject
  private ClienteGeoReferenciaJPA jpa;

  @PostConstruct
  public void load() {
    listClientes = clientBussiness.buscaUltimosClientesAlterados();
    getParameters();
  }

  private void getParameters() {
    clienteId = WebUtil.getParameters("id");
    if (clienteId != null && !clienteId.isEmpty()) {
      cliente = clientBussiness.buscarPorId(Integer.parseInt(clienteId));
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
    boolean sucesso;
    Location location = GoogleMaps.getLocation(cliente.getEndereco());
    if (location != null) {
      ClienteGeoReferencia clienteGeo = new ClienteGeoReferencia();
      clienteGeo.setCliente(cliente);
      clienteGeo.setLatitude(location.getLat());
      clienteGeo.setLongitude(location.getLng());

      ClienteGeoReferencia geoReferenciaAntiga = jpa.buscarClienteGeoReferenciaPorCliente(cliente);
      if (geoReferenciaAntiga == null) {
        jpa.salvar(clienteGeo);
      } else {
        geoReferenciaAntiga.setLatitude(clienteGeo.getLatitude());
        geoReferenciaAntiga.setLongitude(clienteGeo.getLongitude());
        jpa.atualizar(geoReferenciaAntiga);
      }
      sucesso = true;

    } else {
      sucesso = false;
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
