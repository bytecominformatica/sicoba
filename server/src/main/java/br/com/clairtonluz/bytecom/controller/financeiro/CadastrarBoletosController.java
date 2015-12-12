package br.com.clairtonluz.bytecom.controller.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;
import br.com.clairtonluz.bytecom.model.service.financeiro.MensalidadeService;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class CadastrarBoletosController implements Serializable {

    private static final long serialVersionUID = -5517379889465547854L;
    private Mensalidade mensalidade;
    private Cliente cliente;
    private int clienteId;
    private int modalidade;
    private int numeroBoletoInicio;
    private int numeroBoletoFim;
    private double descontoGeracao;
    private double valor;
    private LocalDate dataInicio;

    @Inject
    private MensalidadeService mensalidadeService;
    @Inject
    private ContratoService contratoService;
    @Inject
    private ClienteService clientService;

    @PostConstruct
    public void init() {
        getParameters();
        if (cliente != null) {
            if (mensalidade == null) {
                mensalidade = getNovaMensalidade();
                dataInicio = mensalidade.getDataVencimento();
                valor = mensalidade.getValor();
            }
        }
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clientService.buscarPorId(Integer.parseInt(clienteId));
        }
    }

    public void cadastrarBoletosCaixa() {
        if (boletosNaoRegistrado(numeroBoletoInicio, numeroBoletoFim)) {
            LocalDate c = dataInicio;

            if (numeroBoletoInicio < numeroBoletoFim) {
                for (int i = numeroBoletoInicio; i <= numeroBoletoFim; i++) {
                    gravarBoleto(c, i);
                    c = c.plusMonths(1);
                }
            } else {
                for (int i = numeroBoletoInicio; i >= numeroBoletoFim; i--) {
                    gravarBoleto(c, i);
                    c = c.plusMonths(1);
                }
            }
            AlertaUtil.info("Boletos gerados com sucesso!");
        }
    }

    private void gravarBoleto(LocalDate c, int numeroBoleto) {
        Mensalidade m = mensalidadeService.getNova(cliente, c);
        m.setModalidade(modalidade);
        m.setNumeroBoleto(numeroBoleto);
        m.setDesconto(descontoGeracao);
        m.setValor(valor);
        mensalidadeService.save(m);
    }

    public Mensalidade getNovaMensalidade() {
        Contrato contrato = contratoService.buscarPorCliente(cliente);
        LocalDate d =
                LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento());
        return mensalidadeService.getNova(cliente, d);
    }

    private boolean boletosNaoRegistrado(int inicio, int fim) {
        boolean validos = true;
        List<Mensalidade> listMensalidades = mensalidadeService.buscarPorBoleto(modalidade, inicio, fim);
        if (!listMensalidades.isEmpty()) {
            validos = false;
            StringBuilder sb = new StringBuilder("Os seguintes boletos já estão cadastrados");
            for (Mensalidade m : listMensalidades) {
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

    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Mensalidade mensalidade) {
        this.mensalidade = mensalidade;
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
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
