package br.com.clairtonluz.bytecom.controller.extra;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.extra.ClienteGeoReferencia;
import br.com.clairtonluz.bytecom.model.jpa.extra.ClienteGeoReferenciaJPA;
import br.com.clairtonluz.bytecom.pojo.extra.Location;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.extra.ClienteGeoLocalizacaoService;
import br.com.clairtonluz.bytecom.util.extra.GoogleMaps;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author clairtonluz <br>
 * @author Felipe W. M. Martins <br>
 *         felipewmartins@gmail.com
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
    private ClienteService clienteService;
    @Inject
    private ClienteGeoReferenciaJPA clienteGeoReferenciaJPA;
    private ClienteGeoLocalizacaoService clienteGeoReferenciaService;

    @PostConstruct
    public void load() {
        listClientes = clienteService.buscaUltimosClientesAlterados();
        getParameters();
    }

    private void getParameters() {
        clienteId = WebUtil.getParameters("id");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clienteService.buscarPorId(Integer.parseInt(clienteId));
            cidadeId = cliente.getEndereco().getBairro().getCidade().getId();
            bairroId = cliente.getEndereco().getBairro().getId();
        }
    }

    public void geocodificarTodos() throws IOException {
        int i = 0;
        List<Cliente> clientes = clienteService.findAll();
        for (Cliente c : clientes) {
            if (geocodificar(c)) {
                i++;
            }
        }
        AlertaUtil.info(String.format("Clientes referênciados: %d Clientes não referênciados: %s", i,
                clientes.size() - i));
    }

    public void geocodificar() throws IOException {
        if (geocodificar(cliente)) {
            AlertaUtil.info("Gravado com sucesso");
        } else {
            AlertaUtil.warn("Localização não encontrada");
        }
    }

    public boolean geocodificar(Cliente cliente) throws IOException {
        boolean sucesso = false;
        Location location = GoogleMaps.getLocation(cliente.getEndereco());
        if (location != null) {
            ClienteGeoReferencia clienteGeo = clienteGeoReferenciaService.findByClient(cliente.getId());
            if (clienteGeo == null) {
                clienteGeo = new ClienteGeoReferencia();
                clienteGeo.setCliente(cliente);
            }
            clienteGeo.setLatitude(location.getLat());
            clienteGeo.setLongitude(location.getLng());

            clienteGeoReferenciaService.save(clienteGeo);
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
