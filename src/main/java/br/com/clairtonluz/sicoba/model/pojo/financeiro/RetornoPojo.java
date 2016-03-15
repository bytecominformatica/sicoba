package br.com.clairtonluz.sicoba.model.pojo.financeiro;


import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;

/**
 * Created by clairtonluz on 07/11/15.
 */
public class RetornoPojo {
    private Integer nossoNumero;
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

    public Integer getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(Integer nossoNumero) {
        this.nossoNumero = nossoNumero;
    }
}
