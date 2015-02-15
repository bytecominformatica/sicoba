package net.servehttp.bytecom.financeiro.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.administrador.controller.ServidorController;
import net.servehttp.bytecom.comercial.jpa.entity.StatusCliente;
import net.servehttp.bytecom.financeiro.jpa.MensalidadeJPA;
import net.servehttp.bytecom.financeiro.jpa.entity.Mensalidade;
import net.servehttp.bytecom.financeiro.jpa.entity.StatusMensalidade;
import net.servehttp.bytecom.financeiro.jpa.entity.retorno.Header;
import net.servehttp.bytecom.financeiro.jpa.entity.retorno.HeaderLote;
import net.servehttp.bytecom.financeiro.jpa.entity.retorno.Registro;
import net.servehttp.bytecom.financeiro.service.ArquivoRetornoCaixa;
import net.servehttp.bytecom.util.web.AlertaUtil;

/**
 * 
 * @author clairton
 */
@Named
@RequestScoped
public class RetornoController implements Serializable {

  private static final long serialVersionUID = -3249445210310419657L;

  private Part file;
  @Inject
  private ArquivoRetornoCaixa caixaEJB;
  @Inject
  private ServidorController servidorController;

  private MensalidadeJPA mensalidadeJPA;

  public RetornoController() {}

  public void upload() {
    if (file != null) {
      Header header = null;
      try {
        header = caixaEJB.tratarArquivo(file);
        if (notExists(header)) {

          boolean clienteAtivado = false;

          for (HeaderLote hl : header.getHeaderLotes()) {
            for (Registro r : hl.getRegistros()) {

              Mensalidade m = mensalidadeJPA.buscarMensalidadePorNumeroBoleto(r.getNossoNumero());

              if (m == null) {
                m = mensalidadeJPA.buscarPorId(r.getNossoNumero());
              }

              if (m != null) {
                m.setStatus(StatusMensalidade.PAGO_NO_BOLETO);
                m.setValor(r.getValorTitulo());
                m.setValorPago(r.getRegistroDetalhe().getValorPago());
                m.setDesconto(r.getRegistroDetalhe().getDesconto());
                m.setTarifa(r.getValorTarifa());
                m.setDataOcorrencia(r.getRegistroDetalhe().getDataOcorrencia());
                mensalidadeJPA.atualizar(m);

                if (m.getCliente().getStatus().equals(StatusCliente.INATIVO)) {
                  m.getCliente().setStatus(StatusCliente.ATIVO);
                  mensalidadeJPA.atualizar(m.getCliente());
                  clienteAtivado = true;
                }

              }
            }
          }
          mensalidadeJPA.salvar(header);
          if (clienteAtivado) {
            servidorController.atualizarAcesso();
          }
          AlertaUtil.info("Arquivo enviado com sucesso!");
        }
      } catch (IllegalArgumentException e) {
        AlertaUtil.error("Arquivo corrompido!");
      } catch (Exception e) {
        e.printStackTrace();
        AlertaUtil.error("Arquivo corrompido!");
      }
    } else {
      AlertaUtil.error("Nenhum arquivo selecionado!");
    }
  }

  private boolean notExists(Header header) {
    boolean exists = false;

    List<Header> list = mensalidadeJPA.buscarTodos("sequencial", header.getSequencial(), Header.class);
    if (!list.isEmpty()) {
      exists = true;
      AlertaUtil.error("Arquivo já foi enviado");
    }

    return !exists;
  }

  public Part getFile() {
    return file;
  }

  public void setFile(Part file) {
    this.file = file;
  }

}
