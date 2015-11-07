package net.servehttp.bytecom.pojo.financeiro;

import net.servehttp.bytecom.model.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.model.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.util.StringUtil;

import java.time.LocalDate;

/**
 * Created by clairtonluz on 07/11/15.
 */
public class RetornoPojo {
    private Mensalidade mensalidade;
    private String movimento;

    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Mensalidade mensalidade) {
        this.mensalidade = mensalidade;
    }

    public String getMovimento() {
        return movimento;
    }

    public void setMovimento(String movimento) {
        this.movimento = movimento;
    }
}
