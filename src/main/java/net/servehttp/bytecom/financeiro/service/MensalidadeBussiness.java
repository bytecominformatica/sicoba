package net.servehttp.bytecom.financeiro.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.financeiro.jpa.MensalidadeJPA;
import net.servehttp.bytecom.financeiro.jpa.entity.Cedente;
import net.servehttp.bytecom.financeiro.jpa.entity.Mensalidade;

public class MensalidadeBussiness implements Serializable {

  private static final long serialVersionUID = 8705835474790847188L;
  @Inject
  private MensalidadeJPA mensalidadeJPA;

  public Mensalidade getNovaMensalidade(Cliente cliente, LocalDate vencimento) {
    Mensalidade m = new Mensalidade();
    m.setDataVencimento(vencimento);
    double valorMensalidade = cliente.getContrato().getPlano().getValorMensalidade();
    if (cliente.getContrato().getEquipamentoWifi() != null) {
      valorMensalidade += 5;
    }
    m.setValor(valorMensalidade);
    m.setCliente(cliente);

    return m;
  }

  public byte[] gerarCarne(List<Mensalidade> mensalidades) {
    Cedente cedente = buscarCedente();
    if (cedente == null) {
      throw new IllegalArgumentException("NÃO EXISTEM NENHUM CEDENTE CADASTRADO");
    } else {
      return GerarBoleto.criarCarneCaixa(mensalidades, cedente);
    }
  }

  private Cedente buscarCedente() {
    return mensalidadeJPA.buscarCedente();
  }

  public void remover(Mensalidade m) {
    mensalidadeJPA.remover(m);
  }

  public List<Mensalidade> buscarMensalidadesPorBoleto(int inicio, int fim) {
    return mensalidadeJPA.buscarMensalidadesPorBoletos(inicio, fim);
  }

  public <T> T salvar(T t) {
    return mensalidadeJPA.salvar(t);
  }

  public <T> T atualizar(T t) {
    return mensalidadeJPA.atualizar(t);
  }

}
