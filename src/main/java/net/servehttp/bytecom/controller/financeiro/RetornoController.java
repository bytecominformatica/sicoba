package net.servehttp.bytecom.controller.financeiro;

import net.servehttp.bytecom.controller.extra.GenericoController;
import net.servehttp.bytecom.model.jpa.entity.comercial.StatusCliente;
import net.servehttp.bytecom.model.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.HeaderLote;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Registro;
import net.servehttp.bytecom.model.jpa.financeiro.MensalidadeJPA;
import net.servehttp.bytecom.pojo.financeiro.RetornoPojo;
import net.servehttp.bytecom.service.financeiro.RetornoCaixaService;
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
    private RetornoCaixaService retornoCaixaService;
    @Inject
    private IConnectionControl connectionControl;

    @Inject
    private MensalidadeJPA mensalidadeJPA;
    private List<RetornoPojo> retornoPojos;

    public void upload() {
        if (isFileValid(file)) {
            Header header = null;
            try {
                header = retornoCaixaService.parse(file.getInputStream(), file.getSubmittedFileName());
                retornoPojos = retornoCaixaService.processarHeader(header);
                if(!retornoPojos.isEmpty()){
                    AlertaUtil.info("Arquivo enviado com sucesso!");
                }
            } catch (IllegalArgumentException e) {
                AlertaUtil.error("Arquivo corrompido!");
            } catch (Exception e) {
                AlertaUtil.error(e.getMessage());
                log(e);
            }
        }
    }

    private boolean isFileValid(Part file) {
        boolean valid = true;
        if (file == null) {
            valid = false;
            AlertaUtil.error("Nenhum arquivo selecionado!");
        } else if (!file.getSubmittedFileName().toLowerCase().contains(".ret")) {
            valid = false;
            AlertaUtil.error("Tipo de arquivo inválido");
        } else if (!"application/octet-stream".equals(file.getContentType())) {
            valid = false;
            AlertaUtil.error("Tipo de arquivo inválido");
        }
        return valid;
    }


    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<RetornoPojo> getRetornoPojos() {
        return retornoPojos;
    }

    public void setRetornoPojos(List<RetornoPojo> retornoPojos) {
        this.retornoPojos = retornoPojos;
    }
}
