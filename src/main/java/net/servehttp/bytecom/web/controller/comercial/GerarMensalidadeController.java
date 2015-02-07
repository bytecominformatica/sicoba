package net.servehttp.bytecom.web.controller.comercial;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.business.ClientBussiness;
import net.servehttp.bytecom.business.MensalidadeBussiness;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;
import net.servehttp.bytecom.util.Util;

/**
 *
 * @author clairton
 */
@Named
@ViewScoped
public class GerarMensalidadeController implements Serializable {

  private static final long serialVersionUID = -866830816286480241L;
  private Mensalidade mensalidade;
  private Cliente cliente;
  private int quantidade;
  private double descontoGeracao;
  private LocalDate dataInicio;

  @Inject
  private MensalidadeBussiness business;
  @Inject
  private ClientBussiness clientBussiness;
  @Inject
  private Util util;

  @PostConstruct
  public void init() {
    getParameters();
    if (mensalidade == null) {
      mensalidade = getNovaMensalidade();
      dataInicio = mensalidade.getDataVencimento();
    }
  }

  private void getParameters() {
    String clienteId = util.getParameters("clienteId");
    if (clienteId != null && !clienteId.isEmpty()) {
      cliente = clientBussiness.findById(Integer.parseInt(clienteId));
    }
  }

  public void gerarBoletos() {
    for (int i = 0; i < quantidade; i++) {
      Mensalidade m = new Mensalidade();
      m.setCliente(cliente);
      m.setValor(mensalidade.getValor());
      m.setDesconto(descontoGeracao);
      m.setDataVencimento(dataInicio);
      business.salvar(m);
      dataInicio = dataInicio.plusMonths(1);
    }
    dataInicio = mensalidade.getDataVencimento();
  }

  public Mensalidade getNovaMensalidade() {
    LocalDate d =
        LocalDate.now().plusMonths(1).withDayOfMonth(cliente.getContrato().getVencimento());
    return business.getNovaMensalidade(cliente, d);
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Mensalidade getMensalidade() {
    return mensalidade;
  }

  public void setMensalidade(Mensalidade mensalidade) {
    this.mensalidade = mensalidade;
  }

  public LocalDate getDataInicio() {
    return dataInicio;
  }

  public void setDataInicio(LocalDate dataInicio) {
    this.dataInicio = dataInicio;
  }

  public double getDescontoGeracao() {
    return descontoGeracao;
  }

  public void setDescontoGeracao(double descontoGeracao) {
    this.descontoGeracao = descontoGeracao;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }
}
