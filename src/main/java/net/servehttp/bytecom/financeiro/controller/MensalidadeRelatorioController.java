package net.servehttp.bytecom.financeiro.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.financeiro.jpa.MensalidadeRelatorioJPA;
import net.servehttp.bytecom.financeiro.jpa.entity.Mensalidade;
import net.servehttp.bytecom.financeiro.jpa.entity.StatusMensalidade;
import net.servehttp.bytecom.util.StringUtil;


@Named
@ViewScoped
public class MensalidadeRelatorioController implements Serializable {

  private static final long serialVersionUID = -7284911722827189143L;
  private LocalDate dataInicio;
  private LocalDate dataFim;
  private StatusMensalidade status;
  private boolean pesquisarPorDataOcorrencia = true;

  private double valorTotal;
  private double valorPagoTotal;
  private double descontoTotal;
  private double tarifaTotal;



  private List<Mensalidade> listMensalidades;
  @Inject
  MensalidadeRelatorioJPA mensalidadeRelatorioJPA;

  public MensalidadeRelatorioController() {
    dataInicio = LocalDate.now().withDayOfMonth(1);
    dataFim = LocalDate.now();
    dataFim = dataFim.withDayOfMonth(dataFim.lengthOfMonth());
  }

  public void consultar() {
    listMensalidades =
        mensalidadeRelatorioJPA.buscarPorDataStatus(dataInicio, dataFim, status,
            pesquisarPorDataOcorrencia);
    calcularTotalizadores();
  }

  private void calcularTotalizadores() {
    valorTotal = valorPagoTotal = tarifaTotal = descontoTotal = 0;
    for (Mensalidade m : listMensalidades) {
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

  public List<Mensalidade> getListMensalidades() {
    return listMensalidades;
  }

  public void setListMensalidades(List<Mensalidade> listMensalidades) {
    this.listMensalidades = listMensalidades;
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

  public StatusMensalidade getStatus() {
    return status;
  }

  public void setStatus(StatusMensalidade status) {
    this.status = status;
  }

  public boolean isPesquisarPorDataOcorrencia() {
    return pesquisarPorDataOcorrencia;
  }

  public void setPesquisarPorDataOcorrencia(boolean pesquisarPorDataOcorrencia) {
    this.pesquisarPorDataOcorrencia = pesquisarPorDataOcorrencia;
  }



}
