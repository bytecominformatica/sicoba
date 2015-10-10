package net.servehttp.bytecom.controller.financeiro;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Pagamento;
import net.servehttp.bytecom.persistence.jpa.financeiro.MensalidadeJPA;
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
public class MensalidadeController implements Serializable {

    private static final long serialVersionUID = -866830816286480241L;
    private Mensalidade mensalidade;
    private Pagamento pagamento;
    private List<Mensalidade> mensalidades;

    @Inject
    private MensalidadeService service;
    @Inject
    private PagamentoJPA pagamentoJPA;
    @Inject
    private MensalidadeJPA mensalidadeJPA;
    @Inject
    private ClienteService clientService;
    private Integer clienteId;


    @PostConstruct
    public void init() {
        getParameters();
        if (clienteId != null) {
            buscarMensalidades();
            limpar();
        } else {
            AlertaUtil.error("nenhum cliente selecionado");
        }
    }

    public void buscarMensalidades() {
        mensalidades = mensalidadeJPA.buscarTodosPorClienteDosUltimos6Meses(clienteId);
    }

    private void limpar(){
        ordernarMensalidades();
        if (mensalidade == null) {
            mensalidade = getNovaMensalidade();
        }
    }

    public void removerMensalidadesAbertasNaoVencida() {
        service.removerMensalidadesAbertasNaoVencida(mensalidades);
        AlertaUtil.info("sucesso");
    }

    private void getParameters() {
        String id = WebUtil.getParameters("clienteId");
        if(id != null && !id.isEmpty()) {
            clienteId = Integer.parseInt(id);
        }
    }

    public void gerarBoletosPDF() throws IOException {
        Cliente cliente = mensalidades.get(0).getCliente();
        if (clientePossuiTodosOsDadosNecessarios(cliente)) {
            byte[] pdfData = service.gerarCarne(getBoletosEmAberto(mensalidades));
            WebUtil.downloadPDF(pdfData, cliente.getNome());
        }
    }

    private boolean clientePossuiTodosOsDadosNecessarios(Cliente c) {
        boolean possui = true;
        if (c.getCpfCnpj() == null) {
            possui = false;
            AlertaUtil.error("Cpf ou Cnpj obrigatório");
        }

        List<Mensalidade> list = getBoletosEmAberto(mensalidades);
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
        Collections.sort(mensalidades, new Comparator<Mensalidade>() {
            public int compare(Mensalidade m1, Mensalidade m2) {
                return m1.getDataVencimento().compareTo(m2.getDataVencimento());
            }
        });
    }

    public Mensalidade getNovaMensalidade() {
        Cliente cliente = clientService.buscarPorId(clienteId);
        LocalDate d = LocalDate.now().plusMonths(1).withDayOfMonth(cliente.getContrato().getVencimento());
        return service.getNovaMensalidade(cliente, d);
    }

    public void salvar() {
        mensalidadeJPA.save(mensalidade);

        if (!mensalidades.contains(mensalidade)) {
            mensalidades.add(mensalidade);
            ordernarMensalidades();
        }

        AlertaUtil.info("Mensalidade salva com sucesso!");
        mensalidade = getNovaMensalidade();
        init();
    }

    public void salvarPagamento() {
        pagamentoJPA.save(pagamento);
        AlertaUtil.info("Pagamento salva com sucesso!");
        pagamento = null;
    }

    public void removerMensalidade() {
        if (mensalidade.getPagamentos().isEmpty()) {
            service.remover(mensalidade);
            mensalidades.remove(mensalidade);
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

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }
}
