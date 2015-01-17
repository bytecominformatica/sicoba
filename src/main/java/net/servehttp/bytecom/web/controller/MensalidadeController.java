package net.servehttp.bytecom.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import net.servehttp.bytecom.business.ClientBussiness;
import net.servehttp.bytecom.business.MensalidadeBussiness;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusMensalidade;
import net.servehttp.bytecom.util.AlertaUtil;

import com.servehttp.bytecom.commons.DateUtil;

/**
 *
 * @author clairton
 */
@Named
@ViewScoped
public class MensalidadeController implements Serializable {

  private static final long serialVersionUID = -866830816286480241L;
  private Mensalidade mensalidade;
  private Cliente cliente;
  private int clienteId;
  private Calendar calendar;
  private int numeroBoletoInicio;
  private int numeroBoletoFim;
  private double descontoGeracao;
  private Date dataInicio;
  @Inject
  private MensalidadeBussiness business;
  @Inject
  private ClientBussiness clientBussiness;

  public void load() {
    if (clienteId > 0) {
      cliente = clientBussiness.findById(clienteId);
      ordernarMensalidades();
      if (mensalidade == null) {
        mensalidade = getNovaMensalidade();
        dataInicio = mensalidade.getDataVencimento();
      }
    } else {
      cliente = new Cliente();
      AlertaUtil.error("nenhum cliente selecionado");
    }
  }

  public void gerarBoletosPDF() throws IOException {
    byte[] pdfData = business.gerarCarne(getBoletosEmAberto(cliente.getMensalidades()));

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

    response.reset();
    response.setContentType("application/pdf");
    response.setHeader("Content-disposition",
        String.format("attachment; filename=\"%s.pdf\"", cliente.getNome()));

    OutputStream output = response.getOutputStream();
    output.write(pdfData);
    output.close();

    facesContext.responseComplete();
  }

  private List<Mensalidade> getBoletosEmAberto(List<Mensalidade> list) {
    if (list != null) {
      return list.stream().filter(m -> m.getStatus() == StatusMensalidade.PENDENTE)
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

  public void prepararBaixaMensalidade() {
    if (mensalidade.getStatus() == StatusMensalidade.BAIXA_MANUAL) {
      if (mensalidade.getValorPago() == 0) {
        mensalidade.setValorPago(mensalidade.getValor() - mensalidade.getDesconto());
      }
      if (mensalidade.getDataOcorrencia() == null) {
        mensalidade.setDataOcorrencia(new Date());
      }
    } else if (mensalidade.getStatus() == StatusMensalidade.PENDENTE) {
      mensalidade.setValorPago(0);
      mensalidade.setDataOcorrencia(null);
    }

  }

  public void gerarBoletos() {
    if (boletosNaoRegistrado(numeroBoletoInicio, numeroBoletoFim)) {
      Calendar c = Calendar.getInstance();
      c.setTime(dataInicio);

      if (numeroBoletoInicio < numeroBoletoFim) {
        for (int i = numeroBoletoInicio; i <= numeroBoletoFim; i++) {
          gravarBoleto(c, i);
          c.add(Calendar.MONTH, 1);
        }
      } else {
        for (int i = numeroBoletoInicio; i >= numeroBoletoFim; i--) {
          gravarBoleto(c, i);
          c.add(Calendar.MONTH, 1);
        }

      }
      AlertaUtil.info("Boletos gerados com sucesso!");
    }
  }

  private void gravarBoleto(Calendar c, int numeroBoleto) {
    Mensalidade m = business.getNovaMensalidade(cliente, c.getTime());
    m.setNumeroBoleto(numeroBoleto);
    m.setDesconto(descontoGeracao);
    business.salvar(m);
  }

  public Mensalidade getNovaMensalidade() {
    calendar = DateUtil.getProximoMes();
    calendar.set(Calendar.DAY_OF_MONTH, cliente.getContrato().getVencimento());
    return business.getNovaMensalidade(cliente, calendar.getTime());
  }

  private boolean boletosNaoRegistrado(int inicio, int fim) {
    boolean validos = true;
    List<Mensalidade> listMensalidades = business.buscarMensalidadesPorBoleto(inicio, fim);
    if (!listMensalidades.isEmpty()) {
      validos = false;
      StringBuilder sb = new StringBuilder("Os seguintes boletos já estão cadastrados");
      for (Mensalidade m : listMensalidades) {
        sb.append(" : " + m.getNumeroBoleto());
      }
      AlertaUtil.error(sb.toString());
    }
    return validos;
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
      business.salvar(mensalidade);
      AlertaUtil.info("Mensalidade adicionada com sucesso!");
    } else {
      business.atualizar(mensalidade);
      AlertaUtil.info("Mensalidade atualizado com sucesso!");
    }
    mensalidade = getNovaMensalidade();
    load();
  }

  public String remover(Mensalidade m) {
    business.remover(m);
    mensalidade = getNovaMensalidade();
    load();
    AlertaUtil.info("Mensalidade removido com sucesso!");
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

  public double getDescontoGeracao() {
    return descontoGeracao;
  }

  public void setDescontoGeracao(double descontoGeracao) {
    this.descontoGeracao = descontoGeracao;
  }

}
