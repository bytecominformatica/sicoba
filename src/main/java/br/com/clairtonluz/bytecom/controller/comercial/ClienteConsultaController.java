package br.com.clairtonluz.bytecom.controller.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoService;
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
    private StatusCliente status;

    @Inject
    private ClienteService clienteService;
    @Inject
    private ConexaoService conexaoService;

    @PostConstruct
    public void load() {
        listClientes = clienteService.buscarUltimosAlterados();
    }

    public void atualizarTodasConexoes() throws Exception {
        clienteService.atualizarTodasConexoes();
        AlertaUtil.info("sucesso");
    }

    public Conexao getConexao(Cliente cliente) {
        return conexaoService.buscarPorCliente(cliente);
    }

    public StatusCliente[] getListStatus() {
        return StatusCliente.values();
    }

    public void consultar() {
        listClientes = clienteService.buscarTodosPorNomeIp(nome, ip, status);
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

}
