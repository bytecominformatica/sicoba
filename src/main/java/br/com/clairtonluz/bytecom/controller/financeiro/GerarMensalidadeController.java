package br.com.clairtonluz.bytecom.controller.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.financeiro.MensalidadeService;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class GerarMensalidadeController implements Serializable {

    private static final long serialVersionUID = -866830816286480241L;
    private Mensalidade mensalidade;
    private Cliente cliente;
    private int modalidade;
    private int quantidade;
    private double descontoGeracao;
    private LocalDate dataInicio;

    @Inject
    private MensalidadeService mensalidadeService;
    @Inject
    private ClienteService clientService;

    @PostConstruct
    public void init() {
        getParameters();
        if (cliente != null) {
            if (mensalidade == null) {
                mensalidade = getNovaMensalidade();
                dataInicio = mensalidade.getDataVencimento();
            }
        }
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clientService.buscarPorId(Integer.parseInt(clienteId));
        }
    }

    public void gerarBoletos() {
        for (int i = 0; i < quantidade; i++) {
            Mensalidade m = new Mensalidade();
            m.setModalidade(modalidade);
            m.setCliente(cliente);
            m.setValor(mensalidade.getValor());
            m.setDesconto(descontoGeracao);
            m.setDataVencimento(dataInicio);
            mensalidadeService.save(m);
            dataInicio = dataInicio.plusMonths(1);
        }
        dataInicio = mensalidade.getDataVencimento();
    }

    public Mensalidade getNovaMensalidade() {
        LocalDate d =
                LocalDate.now().plusMonths(1).withDayOfMonth(cliente.getContrato().getVencimento());
        return mensalidadeService.getNovaMensalidade(cliente, d);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Mensalidade mensalidade) {
        this.mensalidade = mensalidade;
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
