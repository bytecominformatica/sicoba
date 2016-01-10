package br.com.clairtonluz.bytecom.controller.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Titulo;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusTitulo;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class TituloController implements Serializable {

    private static final long serialVersionUID = -866830816286480241L;
    private Titulo titulo;


    private List<Titulo> titulos;
    private Cliente cliente;

    @Inject
    private TituloService tituloService;
    @Inject
    private ContratoService contratoService;
    @Inject
    private ClienteService clienteService;

    @PostConstruct
    public void init() {
        getParameters();
        if (cliente != null) {
            buscarCliente();
            ordernarTitulos();
            if (titulo == null) {
                titulo = getNovaTitulo();
            }
        } else {
            cliente = new Cliente();
            AlertaUtil.error("nenhum cliente selecionado");
        }
    }

    public void removerTitulosAbertasNaoVencida() {
        tituloService.removerTodosAbertasNaoVencida(titulos);
        AlertaUtil.info("sucesso");
    }

    public void buscarCliente() {
        cliente = clienteService.buscarPorId(cliente.getId());
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            Cliente cliente = clienteService.buscarPorId(Integer.parseInt(clienteId));
            titulos = tituloService.buscarPorCliente(cliente.getId());
        }

    }

    private void ordernarTitulos() {
        Collections.sort(titulos, new Comparator<Titulo>() {
            public int compare(Titulo m1, Titulo m2) {
                return m1.getDataVencimento().compareTo(m2.getDataVencimento());
            }
        });
    }

    public void prepararBaixaTitulo() {
        if (titulo.getStatus() == StatusTitulo.BAIXA_MANUAL) {
            if (titulo.getValorPago() == 0) {
                titulo.setValorPago(titulo.getValor() - titulo.getDesconto());
            }
            if (titulo.getDataOcorrencia() == null) {
                titulo.setDataOcorrencia(new Date());
            }
        } else if (titulo.getStatus() == StatusTitulo.PENDENTE) {
            titulo.setValorPago(0);
            titulo.setDataOcorrencia(null);
        }
    }

    public Titulo getNovaTitulo() {
        Contrato contrato = contratoService.buscarPorCliente(cliente.getId());
        Date d = DateUtil.toDate(LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento()));
        return tituloService.getNovo(cliente, d);
    }

    public void salvar() throws MensagemException {
        tituloService.save(titulo);

        if (!titulos.contains(titulo)) {
            titulos.add(titulo);
            ordernarTitulos();
        }

        AlertaUtil.info("Titulo salva com sucesso!");
        titulo = getNovaTitulo();
        init();
    }

    public void remover(Titulo m) {
        tituloService.remove(m.getId());
        titulos.remove(m);
        titulo = getNovaTitulo();
        AlertaUtil.info("Titulo removido com sucesso!");
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

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }
}
