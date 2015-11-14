package br.com.clairtonluz.bytecom.controller.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Bairro;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.pojo.comercial.EnderecoPojo;
import br.com.clairtonluz.bytecom.model.service.comercial.AddressService;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author clairtonluz
 */
@Named
@ViewScoped
public class ClienteController implements Serializable {

    private static final long serialVersionUID = 8827281306259995250L;
    private Cliente cliente = new Cliente();
    private List<Cidade> listCidades;
    private List<Bairro> listBairros;
    private Cidade cidade;

    @Inject
    private ClienteService clientService;
    @Inject
    private AddressService addressService;

    @PostConstruct
    public void load() {
        setListCidades(addressService.findCities());
        getParameters();
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("id");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clientService.buscarPorId(Integer.parseInt(clienteId));
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

    public void salvar() throws Exception {
        clientService.save(cliente);
        AlertaUtil.info("sucesso");
    }

    public void buscarEndereco() {
        cidade = null;
        cliente.getEndereco().setLogradouro(null);
        EnderecoPojo ep = addressService.findAddressByCep(cliente.getEndereco().getCep());
        cliente.getEndereco().setBairro(addressService.getNeighborhood(ep));
        listCidades = addressService.findCities();

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
