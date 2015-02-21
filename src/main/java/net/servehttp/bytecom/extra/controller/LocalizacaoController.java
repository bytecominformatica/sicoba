package net.servehttp.bytecom.extra.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.comercial.service.ClientBussiness;
import net.servehttp.bytecom.extra.jpa.entity.ClienteGeoReferencia;
import net.servehttp.bytecom.extra.parser.XMLProcessor;
import net.servehttp.bytecom.extra.service.ClienteGeorefereciaBussiness;
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

  private String geocode_url = "https://maps.googleapis.com/maps/api/geocode/xml";
  private ClienteGeoReferencia clienteGeo = new ClienteGeoReferencia();
  private List<Cliente> listClientes;
  private int cidadeId;
  private int bairroId;
  private String clienteId;
  private Cliente cliente = new Cliente();

  @Inject
  private ClientBussiness clientBussiness;
  @Inject
  private ClienteGeorefereciaBussiness clienteGeoBussiness;

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

  public void geocodificar() {
    String path;
    try {
      path = geocode_url + '?'+"address="+URLEncoder.encode(cliente.getEndereco().getLogradouro(), "UTF-8")+','
          +URLEncoder.encode(cliente.getEndereco().getNumero(), "UTF-8")+','
          +URLEncoder.encode(cliente.getEndereco().getBairro().getNome(), "UTF-8")+"&sensor=false";

      System.out.println("PATH = " + path);
      double latitude = Double.parseDouble(XMLProcessor.INSTANCE.xmlRequest(path)[0]);
      double longitude = Double.parseDouble(XMLProcessor.INSTANCE.xmlRequest(path)[1]);
      clienteGeo.setCliente(getCliente());
      clienteGeo.setLatitude(latitude);
      clienteGeo.setLongitude(longitude);

      clienteGeoBussiness.salvar(clienteGeo);
      AlertaUtil.info("Gravado com sucesso!");


    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }


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
