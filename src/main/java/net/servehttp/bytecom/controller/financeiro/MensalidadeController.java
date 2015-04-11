package net.servehttp.bytecom.controller.financeiro;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.StatusMensalidade;
import net.servehttp.bytecom.service.comercial.ClienteService;
import net.servehttp.bytecom.service.financeiro.MensalidadeService;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

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

  @Inject
  private MensalidadeService business;
  @Inject
  private ClienteService clientBussiness;

  @PostConstruct
  public void init() {
    getParameters();
    if (cliente != null) {
      buscarCliente();
      ordernarMensalidades();
      if (mensalidade == null) {
        mensalidade = getNovaMensalidade();
      }
    } else {
      cliente = new Cliente();
      AlertaUtil.error("nenhum cliente selecionado");
    }
  }

  public void buscarCliente() {
    cliente = clientBussiness.buscarPorId(cliente.getId());
  }

  private void getParameters() {
    String clienteId = WebUtil.getParameters("clienteId");
    if (clienteId != null && !clienteId.isEmpty()) {
      cliente = clientBussiness.buscarPorId(Integer.parseInt(clienteId));
    }

  }

  public void gerarBoletosPDF() throws IOException {
    if (clientePossuiTodosOsDadosNecessarios(cliente)) {
      byte[] pdfData = business.gerarCarne(getBoletosEmAberto(cliente.getMensalidades()));
      WebUtil.downloadPDF(pdfData, cliente.getNome());
    }
  }

  private boolean clientePossuiTodosOsDadosNecessarios(Cliente c) {
    boolean possui = true;
    if (c.getCpfCnpj() == null) {
      possui = false;
      AlertaUtil.error("Cpf ou Cnpj obrigatório");
    }
    
    List<Mensalidade> list = getBoletosEmAberto(cliente.getMensalidades());
    if(list == null || list.isEmpty()) {
      possui = false;
      AlertaUtil.error("Cliente nao possui nenhuma mensalidade em aberto.");
    }
    return possui;
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
        mensalidade.setDataOcorrencia(LocalDate.now());
      }
    } else if (mensalidade.getStatus() == StatusMensalidade.PENDENTE) {
      mensalidade.setValorPago(0);
      mensalidade.setDataOcorrencia(null);
    }
  }

  public Mensalidade getNovaMensalidade() {
    LocalDate d =
        LocalDate.now().plusMonths(1).withDayOfMonth(cliente.getContrato().getVencimento());
    return business.getNovaMensalidade(cliente, d);
  }


  public void salvar() {
    if (mensalidade.getId() == 0) {
      business.salvar(mensalidade);
      cliente.getMensalidades().add(mensalidade);
      ordernarMensalidades();
      AlertaUtil.info("Mensalidade adicionada com sucesso!");
    } else {
      business.atualizar(mensalidade);
      AlertaUtil.info("Mensalidade atualizado com sucesso!");
    }
    mensalidade = getNovaMensalidade();
    init();
  }

  public void remover(Mensalidade m) {
    business.remover(m);
    cliente.getMensalidades().remove(m);
    mensalidade = getNovaMensalidade();
    AlertaUtil.info("Mensalidade removido com sucesso!");
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
}
