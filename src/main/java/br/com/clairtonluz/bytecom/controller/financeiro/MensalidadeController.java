package br.com.clairtonluz.bytecom.controller.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class MensalidadeController implements Serializable {

    private static final long serialVersionUID = -866830816286480241L;
    private Mensalidade mensalidade;


    private List<Mensalidade> mensalidades;
    private Cliente cliente;

    @Inject
    private MensalidadeService mensalidadeService;
    @Inject
    private ContratoService contratoService;
    @Inject
    private ClienteService clienteService;

    @PostConstruct
    public void init() {
        getParameters();
        if (cliente != null) {
            buscarCliente();
            ordernarMensalidades();
            if (mensalidade == null) {
                mensalidade = getNovaMensalidade();
            }
        } else {
            cliente = new Cliente();
            AlertaUtil.error("nenhum cliente selecionado");
        }
    }

    public void removerMensalidadesAbertasNaoVencida() {
        mensalidadeService.removerTodosAbertasNaoVencida(mensalidades);
        AlertaUtil.info("sucesso");
    }

    public void buscarCliente() {
        cliente = clienteService.buscarPorId(cliente.getId());
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            Cliente cliente = clienteService.buscarPorId(Integer.parseInt(clienteId));
            mensalidades = mensalidadeService.buscarPorCliente(cliente);
        }

    }

    private void ordernarMensalidades() {
        Collections.sort(mensalidades, new Comparator<Mensalidade>() {
            public int compare(Mensalidade m1, Mensalidade m2) {
                return m1.getDataVencimento().compareTo(m2.getDataVencimento());
            }
        });
    }

    public void prepararBaixaMensalidade() {
        if (mensalidade.getStatus() == StatusMensalidade.BAIXA_MANUAL) {
            if (mensalidade.getValorPago() == 0) {
                mensalidade.setValorPago(mensalidade.getValor() - mensalidade.getDesconto());
            }
            if (mensalidade.getDataOcorrencia() == null) {
                mensalidade.setDataOcorrencia(LocalDate.now());
            }
        } else if (mensalidade.getStatus() == StatusMensalidade.PENDENTE) {
            mensalidade.setValorPago(0);
            mensalidade.setDataOcorrencia(null);
        }
    }

    public Mensalidade getNovaMensalidade() {
        Contrato contrato = contratoService.buscarPorCliente(cliente);
        LocalDate d =
                LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento());
        return mensalidadeService.getNova(cliente, d);
    }

    public void salvar() {
        mensalidadeService.save(mensalidade);

        if (!mensalidades.contains(mensalidade)) {
            mensalidades.add(mensalidade);
            ordernarMensalidades();
        }

        AlertaUtil.info("Mensalidade salva com sucesso!");
        mensalidade = getNovaMensalidade();
        init();
    }

    public void remover(Mensalidade m) {
        mensalidadeService.remove(m);
        mensalidades.remove(m);
        mensalidade = getNovaMensalidade();
        AlertaUtil.info("Mensalidade removido com sucesso!");
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

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }
}
