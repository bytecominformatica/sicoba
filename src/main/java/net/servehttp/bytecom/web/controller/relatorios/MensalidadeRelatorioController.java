package net.servehttp.bytecom.web.controller.relatorios;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusMensalidade;
import net.servehttp.bytecom.persistence.relatorios.MensalidadeRelatorioJPA;

import com.servehttp.bytecom.commons.DateUtil;
import com.servehttp.bytecom.commons.StringUtil;


@Named
@ViewScoped
public class MensalidadeRelatorioController implements Serializable {

  private static final long serialVersionUID = -7284911722827189143L;
  private Date dataInicio;
  private Date dataFim;
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
    dataInicio = DateUtil.getPrimeiroDiaDoMes().getTime();
    dataFim = DateUtil.getUltimoDiaDoMes().getTime();
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

  public Date getDataInicio() {
    return dataInicio;
  }

  public void setDataInicio(Date dataInicio) {
    this.dataInicio = dataInicio;
  }

  public Date getDataFim() {
    return dataFim;
  }

  public void setDataFim(Date dataFim) {
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
