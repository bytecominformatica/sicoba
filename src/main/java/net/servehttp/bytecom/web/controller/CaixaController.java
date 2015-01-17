package net.servehttp.bytecom.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.ejb.CaixaEJB;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusCliente;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusMensalidade;
import net.servehttp.bytecom.persistence.entity.caixa.Header;
import net.servehttp.bytecom.persistence.entity.caixa.HeaderLote;
import net.servehttp.bytecom.persistence.entity.caixa.Registro;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@Named
@RequestScoped
public class CaixaController implements Serializable {

  private static final long serialVersionUID = -3249445210310419657L;

  private Part file;

  @Inject
  private GenericoJPA genericoJPA;
  @Inject
  private CaixaEJB caixaEJB;
  @Inject
  private ServidorController servidorController;

  public CaixaController() {}

  public void upload() {
    if (file != null) {
      Header header = null;
      try {
        header = caixaEJB.tratarArquivo(file);
        if (notExists(header)) {
          
          boolean clienteAtivado = false;
         
          for (HeaderLote hl : header.getHeaderLotes()) {
            for (Registro r : hl.getRegistros()) {
              Mensalidade m =
                  genericoJPA.buscarUm("numeroBoleto", r.getNossoNumero(), Mensalidade.class);
              if (m != null) {
                m.setStatus(StatusMensalidade.PAGO_NO_BOLETO);
                m.setValor(r.getValorTitulo());
                m.setValorPago(r.getRegistroDetalhe().getValorPago());
                m.setDesconto(r.getRegistroDetalhe().getDesconto());
                m.setTarifa(r.getValorTarifa());
                m.setDataOcorrencia(r.getRegistroDetalhe().getDataOcorrencia());
                genericoJPA.atualizar(m);
                
                if (m.getCliente().getStatus().equals(StatusCliente.INATIVO)) {
                  m.getCliente().setStatus(StatusCliente.ATIVO);
                  genericoJPA.atualizar(m.getCliente());
                  clienteAtivado = true;
                }
                
              }
            }
          }
          genericoJPA.salvar(header);
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

    List<Header> list = genericoJPA.buscarTodos("sequencial", header.getSequencial(), Header.class);
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
