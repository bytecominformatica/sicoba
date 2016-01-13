package br.com.clairtonluz.bytecom.controller.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno.Header;
import br.com.clairtonluz.bytecom.pojo.financeiro.RetornoPojo;
import br.com.clairtonluz.bytecom.model.service.financeiro.RetornoCaixaService;
import br.com.clairtonluz.bytecom.model.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;

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
public class RetornoController implements Serializable {

    private static final long serialVersionUID = -3249445210310419657L;

    private Part file;
    @Inject
    private RetornoCaixaService retornoCaixaService;
    @Inject
    private IConnectionControl connectionControl;

    private List<RetornoPojo> retornoPojos;

    public void upload() throws Exception {
        if (isFileValid(file)) {
            Header header = retornoCaixaService.parse(file.getInputStream(), file.getSubmittedFileName());
            retornoPojos = retornoCaixaService.processarHeader(header);
            if (!retornoPojos.isEmpty()) {
                AlertaUtil.info("Arquivo enviado com sucesso!");
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
