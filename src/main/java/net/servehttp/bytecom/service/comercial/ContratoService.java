package net.servehttp.bytecom.service.comercial;

import net.servehttp.bytecom.model.jpa.comercial.ContratoJPA;
import net.servehttp.bytecom.model.jpa.entity.comercial.Contrato;
import net.servehttp.bytecom.model.jpa.extra.GenericoJPA;

import javax.inject.Inject;
import java.io.Serializable;

public class ContratoService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private ContratoJPA contratoJPA;
    @Inject
    private GenericoJPA jpa;

    public void remover(Contrato c) {
        contratoJPA.remover(c);
    }

    public Contrato buscarPorId(int id) {
        return jpa.buscarPorId(Contrato.class, id);
    }

}
