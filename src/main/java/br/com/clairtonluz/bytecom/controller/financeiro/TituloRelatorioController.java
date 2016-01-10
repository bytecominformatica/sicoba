package br.com.clairtonluz.bytecom.controller.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Titulo;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.bytecom.model.jpa.financeiro.TituloRelatorioJPA;
import br.com.clairtonluz.bytecom.util.StringUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Named
@ViewScoped
public class TituloRelatorioController implements Serializable {

    private static final long serialVersionUID = -7284911722827189143L;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusTitulo status;
    private boolean pesquisarPorDataOcorrencia = true;

    private double valorTotal;
    private double valorPagoTotal;
    private double descontoTotal;
    private double tarifaTotal;


    private List<Titulo> listTitulos;
    @Inject
    TituloRelatorioJPA tituloRelatorioJPA;

    public TituloRelatorioController() {
        dataInicio = LocalDate.now().withDayOfMonth(1);
        dataFim = LocalDate.now();
        dataFim = dataFim.withDayOfMonth(dataFim.lengthOfMonth());
    }

    public void consultar() {
        listTitulos =
                tituloRelatorioJPA.buscarPorDataStatus(dataInicio, dataFim, status,
                        pesquisarPorDataOcorrencia);
        calcularTotalizadores();
    }

    private void calcularTotalizadores() {
        valorTotal = valorPagoTotal = tarifaTotal = descontoTotal = 0;
        for (Titulo m : listTitulos) {
            valorTotal += m.getValor();
            valorPagoTotal += m.getValorPago();
            descontoTotal += m.getDesconto();
            tarifaTotal += m.getTarifa();

        }

    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public List<Titulo> getListTitulos() {
        return listTitulos;
    }

    public void setListTitulos(List<Titulo> listTitulos) {
        this.listTitulos = listTitulos;
    }

    public String getValorTotal() {
        return StringUtil.formatCurrence(valorTotal);
    }

    public String getValorPagoTotal() {
        return StringUtil.formatCurrence(valorPagoTotal);
    }

    public String getTarifaTotal() {
        return StringUtil.formatCurrence(tarifaTotal);
    }

    public String getDescontoTotal() {
        return StringUtil.formatCurrence(descontoTotal);
    }

    public StatusTitulo getStatus() {
        return status;
    }

    public void setStatus(StatusTitulo status) {
        this.status = status;
    }

    public boolean isPesquisarPorDataOcorrencia() {
        return pesquisarPorDataOcorrencia;
    }

    public void setPesquisarPorDataOcorrencia(boolean pesquisarPorDataOcorrencia) {
        this.pesquisarPorDataOcorrencia = pesquisarPorDataOcorrencia;
    }


}
