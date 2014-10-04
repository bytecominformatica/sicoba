package net.servehttp.bytecom.web.service.maps;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import net.servehttp.bytecom.business.ClientBussiness;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.maps.ClienteGeoReferencia;
import net.servehttp.bytecom.util.Util;
import net.servehttp.bytecom.web.controller.ClienteController;

@Named
@ViewScoped
public class LocalizacaoController implements Serializable {

  private static final long serialVersionUID = -6695262369077987911L;
  private  String geocode_url = "https://maps.googleapis.com/maps/api/geocode/xml";
  private ClienteGeoReferencia clienteGeo = new ClienteGeoReferencia();
  private List<Cliente> listClientes;
  private int cidadeId;
  private int bairroId;
  private Cliente cliente = new Cliente();
  
  @Inject
  private ClientBussiness clientBussiness;
  @Inject
  private Util util;
  
  @PostConstruct
  public void load(){
    listClientes = clientBussiness.buscaUltimosClientesAlterados();
    getParameters();
  }
  
  private void getParameters(){
    String clienteId = util.getParameters("id");
    if (clienteId != null && !clienteId.isEmpty()) {
      cliente = clientBussiness.findById(Integer.parseInt(clienteId));
      cidadeId = cliente.getEndereco().getBairro().getCidade().getId();
      bairroId = cliente.getEndereco().getBairro().getId();
      //atualizaBairros();
    }
  }
  
  public void geocodificar(){
    String path;
    try {
      path = geocode_url + '?'+"address="+URLEncoder.encode(cliente.getEndereco().getLogradouro(), "UTF-8")+','+URLEncoder.encode(cliente.getEndereco().getNumero(), "UTF-8")+','+URLEncoder.encode(cliente.getEndereco().getBairro().getNome(), "UTF-8")+"&sensor=false";
      XMLProcessor xmlPrc = new XMLProcessor();
      xmlPrc.xmlRequest(path);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
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
  
  
 
  
}
