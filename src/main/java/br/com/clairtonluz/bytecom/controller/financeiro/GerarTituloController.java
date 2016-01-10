package br.com.clairtonluz.bytecom.controller.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Titulo;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;
import br.com.clairtonluz.bytecom.model.service.financeiro.TituloService;
import br.com.clairtonluz.bytecom.util.DateUtil;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class GerarTituloController implements Serializable {

    private static final long serialVersionUID = -866830816286480241L;
    private Titulo titulo;
    private Cliente cliente;
    private int modalidade;
    private int quantidade;
    private double descontoGeracao;
    private Date dataInicio;

    @Inject
    private TituloService tituloService;
    @Inject
    private ClienteService clientService;
    @Inject
    private ContratoService contratoService;

    @PostConstruct
    public void init() {
        getParameters();
        if (cliente != null) {
            if (titulo == null) {
                titulo = getNovaTitulo();
                dataInicio = titulo.getDataVencimento();
            }
        }
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clientService.buscarPorId(Integer.parseInt(clienteId));
        }
    }

    public Titulo getNovaTitulo() {
        Contrato contrato = contratoService.buscarPorCliente(cliente.getId());
        Date d = DateUtil.toDate(LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento()));
        return tituloService.getNovo(cliente, d);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public double getDescontoGeracao() {
        return descontoGeracao;
    }

    public void setDescontoGeracao(double descontoGeracao) {
        this.descontoGeracao = descontoGeracao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getModalidade() {
        return modalidade;
    }

    public void setModalidade(int modalidade) {
        this.modalidade = modalidade;
    }
}
