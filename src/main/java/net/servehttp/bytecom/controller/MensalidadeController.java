package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
  private Mensalidade mensalidade;
  @Inject
  private GenericoJPA genericoJPA;
  private Cliente cliente;
  private int clienteId;
  private Calendar calendar;
  private int numeroBoletoInicio;
  private int numeroBoletoFim;
  private Date dataInicio;

  public void load() {
    if (clienteId > 0) {
      cliente = genericoJPA.buscarPorId(Cliente.class, clienteId);
      
      ordernarMensalidades();
      if (mensalidade == null) {
        novaMensalidade();
        dataInicio = mensalidade.getDataVencimento();
      }
    }
  }

  private void ordernarMensalidades() {
    Collections.sort(cliente.getMensalidades(), new Comparator<Mensalidade>() {
        public int compare(Mensalidade m1, Mensalidade m2) {
            return m1.getDataVencimento().compareTo(m2.getDataVencimento());
        }
    });
  }

  public void gerarBoletos() {
    if (isBoletosValidos(numeroBoletoInicio, numeroBoletoFim)) {
      Calendar c = Calendar.getInstance();
      c.setTime(dataInicio);

      Mensalidade m;
      for (int i = numeroBoletoInicio; i <= numeroBoletoFim; i++) {
        m = new Mensalidade();
        m.setDataVencimento(c.getTime());
        m.setValor(cliente.getContrato().getPlano().getValorMensalidade());
        m.setCliente(cliente);
        m.setNumeroBoleto(i);

        c.add(Calendar.MONTH, 1);
        genericoJPA.salvar(m);
      }

      AlertaUtil.alerta("Boletos gerados com sucesso!");
    }
  }

  public boolean isBoletosValidos(int inicio, int fim) {
    boolean validos = true;

    if (inicio > fim) {
      validos = false;
      AlertaUtil.alerta("número do boleto início não pode ser maior que o número do boleto fim",
          AlertaUtil.WARN);
    } else {
      List<Mensalidade> listMensalidades = genericoJPA.buscarJpql("select m from Mensalidade m where m.numeroBoleto between ?1 and ?2 ", inicio, fim, Mensalidade.class);
      if(!listMensalidades.isEmpty()){
        validos = false;
        StringBuilder sb = new StringBuilder("Os seguintes boletos já estão cadastrados");
        for(Mensalidade m : listMensalidades){
          sb.append(" : " + m.getNumeroBoleto());
        }
        AlertaUtil.alerta(sb.toString(), AlertaUtil.WARN);
      }
    }
    return validos;
  }

  public void novaMensalidade() {
    mensalidade = new Mensalidade();
    if (mensalidade.getDataVencimento() == null) {
      calendar = DateUtil.INSTANCE.getProximoMes();
      calendar.set(Calendar.DAY_OF_MONTH, cliente.getContrato().getVencimento());
      mensalidade.setDataVencimento(calendar.getTime());
    }
    mensalidade.setValor(cliente.getContrato().getPlano().getValorMensalidade());
    mensalidade.setCliente(cliente);
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

  public String remover(Mensalidade m) {
    System.out.println("MENSALIDADE = " + m.getId() + " - " + m.getDataVencimentoFormatada());
    cliente.getMensalidades().remove(m);
    genericoJPA.remover(m);
    load();
    AlertaUtil.alerta("Mensalidade removido com sucesso!");
    return null;
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

  public int getNumeroBoletoInicio() {
    return numeroBoletoInicio;
  }

  public void setNumeroBoletoInicio(int numeroBoletoInicio) {
    this.numeroBoletoInicio = numeroBoletoInicio;
  }

  public int getNumeroBoletoFim() {
    return numeroBoletoFim;
  }

  public void setNumeroBoletoFim(int numeroBoletoFim) {
    this.numeroBoletoFim = numeroBoletoFim;
  }

  public Date getDataInicio() {
    return dataInicio;
  }

  public void setDataInicio(Date dataInicio) {
    this.dataInicio = dataInicio;
  }

}
