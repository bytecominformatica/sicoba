package net.servehttp.bytecom.controller.financeiro;

import net.servehttp.bytecom.controller.extra.GenericoController;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Pagamento;
import net.servehttp.bytecom.persistence.jpa.financeiro.PagamentoJPA;
import net.servehttp.bytecom.service.comercial.ClienteService;
import net.servehttp.bytecom.service.financeiro.MensalidadeService;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
 */
@Named
@ViewScoped
public class MensalidadeController extends GenericoController implements Serializable {

    private static final long serialVersionUID = -866830816286480241L;
    private Mensalidade mensalidade;
    private Pagamento pagamento;
    private Cliente cliente;

    @Inject
    private MensalidadeService service;
    @Inject
    private PagamentoJPA pagamentoJPA;
    @Inject
    private ClienteService clientService;

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
        service.removerMensalidadesAbertasNaoVencida(cliente);
        AlertaUtil.info("sucesso");
    }

    public void buscarCliente() {
        cliente = clientService.buscarPorId(cliente.getId());
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clientService.buscarPorId(Integer.parseInt(clienteId));
        }
    }

    public void gerarBoletosPDF() throws IOException {
        if (clientePossuiTodosOsDadosNecessarios(cliente)) {
            byte[] pdfData = service.gerarCarne(getBoletosEmAberto(cliente.getMensalidades()));
            WebUtil.downloadPDF(pdfData, cliente.getNome());
        }
    }

    private boolean clientePossuiTodosOsDadosNecessarios(Cliente c) {
        boolean possui = true;
        if (c.getCpfCnpj() == null) {
            possui = false;
            AlertaUtil.error("Cpf ou Cnpj obrigatório");
        }

        List<Mensalidade> list = getBoletosEmAberto(cliente.getMensalidades());
        if (list == null || list.isEmpty()) {
            possui = false;
            AlertaUtil.error("Cliente nao possui nenhuma mensalidade em aberto.");
        }
        return possui;
    }

    private List<Mensalidade> getBoletosEmAberto(List<Mensalidade> list) {
        if (list != null) {
            return list.stream().filter(m -> m.getPagamentos().isEmpty())
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return null;
    }

    private void ordernarMensalidades() {
        Collections.sort(cliente.getMensalidades(), new Comparator<Mensalidade>() {
            public int compare(Mensalidade m1, Mensalidade m2) {
                return m1.getDataVencimento().compareTo(m2.getDataVencimento());
            }
        });
    }

    public Mensalidade getNovaMensalidade() {
        LocalDate d = LocalDate.now().plusMonths(1).withDayOfMonth(cliente.getContrato().getVencimento());
        return service.getNovaMensalidade(cliente, d);
    }

    public void salvar() {
        jpa.salvar(mensalidade);

        if (!cliente.getMensalidades().contains(mensalidade)) {
            cliente.getMensalidades().add(mensalidade);
            ordernarMensalidades();
        }

        AlertaUtil.info("Mensalidade salva com sucesso!");
        mensalidade = getNovaMensalidade();
        init();
    }

    public void salvarPagamento() {
        jpa.salvar(pagamento);
        AlertaUtil.info("Pagamento salva com sucesso!");
        pagamento = null;
    }

    public void removerMensalidade() {
        if(mensalidade.getPagamentos().isEmpty()) {
            service.remover(mensalidade);
            cliente.getMensalidades().remove(mensalidade);
            mensalidade = getNovaMensalidade();
            AlertaUtil.info("Mensalidade removido com sucesso!");
        } else {
            AlertaUtil.error("Mensalidade possui pagamentos e não pode ser removida");
        }
    }

    public void removerPagamento() {
        pagamentoJPA.remove(pagamento);
        pagamento = null;
        AlertaUtil.info("Pagamento removido com sucesso!");
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

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
