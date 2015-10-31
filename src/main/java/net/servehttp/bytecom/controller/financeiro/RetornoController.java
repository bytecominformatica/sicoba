package net.servehttp.bytecom.controller.financeiro;

import net.servehttp.bytecom.commons.parse.IParseUpload;
import net.servehttp.bytecom.controller.extra.GenericoController;
import net.servehttp.bytecom.model.jpa.entity.comercial.StatusCliente;
import net.servehttp.bytecom.model.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.HeaderLote;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Registro;
import net.servehttp.bytecom.model.jpa.financeiro.MensalidadeJPA;
import net.servehttp.bytecom.service.provedor.IConnectionControl;
import net.servehttp.bytecom.util.web.AlertaUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.List;

/**
 * @author clairton
 */
@Named
@RequestScoped
public class RetornoController extends GenericoController implements Serializable {

    private static final long serialVersionUID = -3249445210310419657L;

    private Part file;
    @Inject
    private IParseUpload<Header> parserUpload;
    @Inject
    private IConnectionControl connectionControl;

    @Inject
    private MensalidadeJPA mensalidadeJPA;

    public void upload() {
        if (file != null) {
            Header header = null;
            try {
                header = parserUpload.parse(file);
                if (notExists(header)) {

                    for (HeaderLote hl : header.getHeaderLotes()) {
                        for (Registro r : hl.getRegistros()) {

                            Mensalidade m = mensalidadeJPA.buscarPorModalidadeNumeroBoleto(r.getModalidadeNossoNumero(), r.getNossoNumero());

                            if (m == null) {
                                m = jpa.buscarPorId(Mensalidade.class, r.getNossoNumero());
                            }

                            if (m != null) {
                                m.setStatus(StatusMensalidade.PAGO_NO_BOLETO);
                                m.setValor(r.getValorTitulo());
                                m.setValorPago(r.getRegistroDetalhe().getValorPago());
                                m.setDesconto(r.getRegistroDetalhe().getDesconto());
                                m.setTarifa(r.getValorTarifa());
                                m.setDataOcorrencia(r.getRegistroDetalhe().getDataOcorrencia());
                                jpa.salvar(m);

                                if (m.getCliente().getStatus().equals(StatusCliente.INATIVO)) {
                                    m.getCliente().setStatus(StatusCliente.ATIVO);
                                    m.getCliente().getStatus().atualizarConexao(m.getCliente(), connectionControl);
                                    jpa.salvar(m.getCliente());
                                }

                            }
                        }
                    }
                    jpa.salvar(header);
                    AlertaUtil.info("Arquivo enviado com sucesso!");
                }
            } catch (IllegalArgumentException e) {
                AlertaUtil.error("Arquivo corrompido!");
            } catch (Exception e) {
                AlertaUtil.error("Arquivo corrompido!");
                log(e);
            }
        } else {
            AlertaUtil.error("Nenhum arquivo selecionado!");
        }
    }

    private boolean notExists(Header header) {
        boolean exists = false;

        List<Header> list = jpa.buscarTodos("sequencial", header.getSequencial(), Header.class);
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
