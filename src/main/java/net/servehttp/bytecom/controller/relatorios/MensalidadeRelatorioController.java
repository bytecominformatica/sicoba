package net.servehttp.bytecom.controller.relatorios;

import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.entity.Mensalidade;
import net.servehttp.bytecom.persistence.relatorios.MensalidadeRelatorioJPA;


@Named
@ViewScoped
public class MensalidadeRelatorioController {

  private Date dataInicio;
  private Date dataFim;
  private int status;

  private List<Mensalidade> listMensalidades;
  @Inject
  MensalidadeRelatorioJPA mensalidadeRelatorioJPA;

  public void consultar() {
    listMensalidades = mensalidadeRelatorioJPA.buscarPorDataStatus(dataInicio, dataFim, status);
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



}
