package br.com.clairtonluz.bytecom.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ContratoJPA;
import br.com.clairtonluz.bytecom.model.jpa.extra.GenericoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;

import javax.inject.Inject;
import java.io.Serializable;

public class ContratoService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private ContratoJPA contratoJPA;
    @Inject
    private GenericoJPA jpa;

    public void save(Contrato c) {
        contratoJPA.save(c);
    }
    public void remove(Contrato c) {
        contratoJPA.remove(c);
    }

    public Contrato buscarPorId(int id) {
        return jpa.buscarPorId(Contrato.class, id);
    }

}
