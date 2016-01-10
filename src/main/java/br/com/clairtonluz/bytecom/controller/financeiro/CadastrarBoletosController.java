package br.com.clairtonluz.bytecom.controller.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Titulo;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;
import br.com.clairtonluz.bytecom.model.service.financeiro.TituloService;
import br.com.clairtonluz.bytecom.util.DateUtil;
import br.com.clairtonluz.bytecom.util.MensagemException;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class CadastrarBoletosController implements Serializable {

    private static final long serialVersionUID = -5517379889465547854L;
    private Titulo titulo;
    private Cliente cliente;
    private int clienteId;
    private int modalidade;
    private int numeroBoletoInicio;
    private int numeroBoletoFim;
    private double descontoGeracao;
    private double valor;
    private Date dataInicio;

    @Inject
    private TituloService tituloService;
    @Inject
    private ContratoService contratoService;
    @Inject
    private ClienteService clientService;

    @PostConstruct
    public void init() {
        getParameters();
        if (cliente != null) {
            if (titulo == null) {
                titulo = getNovaTitulo();
                dataInicio = titulo.getDataVencimento();
                valor = titulo.getValor();
            }
        }
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clientService.buscarPorId(Integer.parseInt(clienteId));
        }
    }

    public void cadastrarBoletosCaixa() throws MensagemException {
        if (boletosNaoRegistrado(numeroBoletoInicio, numeroBoletoFim)) {
            Date c = dataInicio;

            if (numeroBoletoInicio < numeroBoletoFim) {
                for (int i = numeroBoletoInicio; i <= numeroBoletoFim; i++) {
                    gravarBoleto(c, i);
                    LocalDateTime x = DateUtil.toLocalDateTime(c).plusMonths(1);
                    c = DateUtil.toDate(x);
                }
            } else {
                for (int i = numeroBoletoInicio; i >= numeroBoletoFim; i--) {
                    gravarBoleto(c, i);
                    LocalDateTime x = DateUtil.toLocalDateTime(c).plusMonths(1);
                    c = DateUtil.toDate(x);
                }
            }
            AlertaUtil.info("Boletos gerados com sucesso!");
        }
    }

    private void gravarBoleto(Date c, int numeroBoleto) throws MensagemException {
        Titulo m = tituloService.getNovo(cliente, c);
        m.setModalidade(modalidade);
        m.setNumeroBoleto(numeroBoleto);
        m.setDesconto(descontoGeracao);
        m.setValor(valor);
        tituloService.save(m);
    }

    public Titulo getNovaTitulo() {
        Contrato contrato = contratoService.buscarPorCliente(cliente.getId());
        Date d = DateUtil.toDate(LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento()));
        return tituloService.getNovo(cliente, d);
    }

    private boolean boletosNaoRegistrado(int inicio, int fim) {
        boolean validos = true;
        List<Titulo> listTitulos = tituloService.buscarPorBoleto(inicio, fim);
        if (!listTitulos.isEmpty()) {
            validos = false;
            StringBuilder sb = new StringBuilder("Os seguintes boletos já estão cadastrados");
            for (Titulo m : listTitulos) {
                sb.append(" : " + m.getModalidade() + '-' + m.getNumeroBoleto());
            }
            AlertaUtil.error(sb.toString());
        }
        return validos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public int getNumeroBoletoInicio() {
        return numeroBoletoInicio;
    }

    public void setNumeroBoletoInicio(int numeroBoletoInicio) {
        this.numeroBoletoInicio = numeroBoletoInicio;
    }

    public int getNumeroBoletoFim() {
        return numeroBoletoFim;
    }

    public void setNumeroBoletoFim(int numeroBoletoFim) {
        this.numeroBoletoFim = numeroBoletoFim;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getModalidade() {
        return modalidade;
    }

    public void setModalidade(int modalidade) {
        this.modalidade = modalidade;
    }
}
