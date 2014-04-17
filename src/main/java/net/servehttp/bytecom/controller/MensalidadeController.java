package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.MensalidadeJPA;
import net.servehttp.bytecom.persistence.entity.Mensalidade;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.DateUtil;

/**
 *
 * @author clairton
 */
@ManagedBean
@ViewScoped
public class MensalidadeController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -866830816286480241L;
	private List<Mensalidade> listMensalidades;
    private Mensalidade mensalidadeSelecionada = new Mensalidade();
    private Mensalidade novoMensalidade = new Mensalidade();
    @Inject
    private MensalidadeJPA mensalidadeJPA;
    @Inject
    private GenericoJPA genericoJPA;
    private Date dataVencimento;

    private Calendar calendar;

    public MensalidadeController() {
    }

    @PostConstruct
    public void load() {
        exibirMensalidades();
        desmarcaItenSelecionado();
        init();
    }

    private void init() {
        dataVencimento = DateUtil.INSTANCE.getHoje();
        calendar = DateUtil.getProximoMes();
        calendar.set(Calendar.DAY_OF_MONTH, novoMensalidade.getCliente().getContrato().getVencimento());

        novoMensalidade.setValor(novoMensalidade.getCliente().getContrato().getPlano().getValorMensalidade());
    }

    private void desmarcaItenSelecionado() {
        mensalidadeSelecionada = null;
    }
//
//    public StreamedContent getRecibo() {
//        if (mensalidadeSelecionada != null) {
//            List<Mensalidade> mensalidades = new ArrayList<>();
//            mensalidades.add(mensalidadeSelecionada);
//            InputStream stream = new ByteArrayInputStream(new MensalidadeRelatorioEJB().getRecibos(mensalidades));
//            Locale locale = new Locale("pt", "BR");
//            return new DefaultStreamedContent(stream, "application/pdf", "Mensalidade de " + new SimpleDateFormat("MMMM/yyyy", locale).format(new Date()) + ".pdf");
//        } else {
//            AlertaHelper.alertaError("Selecione uma mensalidade!");
//            return null;
//        }
//    }

    public List<Mensalidade> getListMensalidades() {
        return listMensalidades;
    }

    public void setListMensalidades(List<Mensalidade> listMensalidades) {
        this.listMensalidades = listMensalidades;
    }

    public int getMes() {
        return calendar.get(Calendar.MONTH);
    }

    public void setMes(int mes) {
        calendar.set(Calendar.MONTH, mes);
    }

    public int getAno() {
        return calendar.get(Calendar.YEAR);
    }

    public void setAno(int ano) {
        calendar.set(Calendar.YEAR, ano);
    }

    public Mensalidade getMensalidadeSelecionada() {
        return mensalidadeSelecionada;
    }

    public void setMensalidadeSelecionada(Mensalidade mensalidadeSelecionada) {
        this.mensalidadeSelecionada = mensalidadeSelecionada;
    }

    public Mensalidade getNovoMensalidade() {
        return novoMensalidade;
    }

    public void setNovoMensalidade(Mensalidade novoMensalidade) {
        this.novoMensalidade = novoMensalidade;
    }

    public void salvar() {
        try {
            dataVencimento = calendar.getTime();
            novoMensalidade.setDataVencimento(dataVencimento);
            genericoJPA.salvar(novoMensalidade);
            load();
            AlertaUtil.alerta("Mensalidade adicionada com sucesso!");
        } catch (EJBException e) {
            if (mensalidadeJPA.buscarMensalidadesPorClienteEVencimento(novoMensalidade.getCliente(), dataVencimento) != null) {
                AlertaUtil.alerta("Mensalidade já Cadastrada", AlertaUtil.ERROR);
            } else {
                throw e;
            }
        }
    }

    public void atualizar() {
        genericoJPA.atualizar(mensalidadeSelecionada);
        load();
        AlertaUtil.alerta("Mensalidade atualizado com sucesso!");
    }

    public void remover() {
        genericoJPA.remover(mensalidadeSelecionada);
        load();
        AlertaUtil.alerta("Mensalidade removido com sucesso!");
    }

    private void exibirMensalidades() {
        if (novoMensalidade.getCliente() != null) {
            listMensalidades = mensalidadeJPA.buscarMensalidadePorCliente(novoMensalidade.getCliente());
        }
    }
}
