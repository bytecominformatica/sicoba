package br.com.clairtonluz.bytecom.controller.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;

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
public class ClienteConsultaController implements Serializable {

    private static final long serialVersionUID = 8827281306259995250L;
    private List<Cliente> listClientes;
    private String nome;
    private String ip;
    private String mac;
    private StatusCliente status;

    @Inject
    private ClienteService service;

    @PostConstruct
    public void load() {
        listClientes = service.buscaUltimosClientesAlterados();
    }

    public void atualizarTodasConexoes() throws Exception {
        service.atualizarTodasConexoes();
        AlertaUtil.info("sucesso");
    }

    public StatusCliente[] getListStatus() {
        return StatusCliente.values();
    }

    public void consultar() {
        listClientes = service.buscarTodosPorNomeIp(nome, ip, mac, status);
    }

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusCliente getStatus() {
        return status;
    }

    public void setStatus(StatusCliente status) {
        this.status = status;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
