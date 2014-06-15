package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Cliente;
import net.servehttp.bytecom.persistence.entity.Mensalidade;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.DateUtil;

/**
 *
 * @author clairton
 */
@Named
@ViewScoped
public class MensalidadeController implements Serializable {

  private static final long serialVersionUID = -866830816286480241L;
  private List<Mensalidade> listMensalidades;
  private Mensalidade mensalidade;
  @Inject
  private GenericoJPA genericoJPA;
  private Cliente cliente;
  private int clienteId;

  private Calendar calendar;

  public void load() {
    if (clienteId > 0) {
      cliente = genericoJPA.buscarPorId(Cliente.class, clienteId);
      if (mensalidade == null) {
        novaMensalidade();
      }
    }
  }

  private void novaMensalidade() {
    mensalidade = new Mensalidade();
    if (mensalidade.getDataVencimento() == null) {
      calendar = DateUtil.getProximoMes();
      calendar.set(Calendar.DAY_OF_MONTH, cliente.getContrato().getVencimento());
      mensalidade.setDataVencimento(calendar.getTime());
    }
    mensalidade.setValor(cliente.getContrato().getPlano().getValorMensalidade());
    mensalidade.setCliente(cliente);
  }

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

  public void salvar() {
    if (mensalidade.getId() == 0) {
      genericoJPA.salvar(mensalidade);
      AlertaUtil.alerta("Mensalidade adicionada com sucesso!");
    } else {
      genericoJPA.atualizar(mensalidade);
      AlertaUtil.alerta("Mensalidade atualizado com sucesso!");
    }
    novaMensalidade();
    load();
  }

  public void remover() {
    genericoJPA.remover(mensalidade);
    novaMensalidade();
    load();
    AlertaUtil.alerta("Mensalidade removido com sucesso!");
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public int getClienteId() {
    return clienteId;
  }

  public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
  }

  public Mensalidade getMensalidade() {
    return mensalidade;
  }

  public void setMensalidade(Mensalidade mensalidade) {
    this.mensalidade = mensalidade;
  }
}
