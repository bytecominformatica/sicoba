package br.com.clairtonluz.bytecom.pojo.financeiro;

import br.com.clairtonluz.bytecom.model.entity.financeiro.Titulo;

/**
 * Created by clairtonluz on 07/11/15.
 */
public class RetornoPojo {
    private Titulo titulo;
    private String movimento;

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public String getMovimento() {
        return movimento;
    }

    public void setMovimento(String movimento) {
        this.movimento = movimento;
    }
}
