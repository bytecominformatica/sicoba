package br.com.clairtonluz.bytecom.pojo.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;

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
