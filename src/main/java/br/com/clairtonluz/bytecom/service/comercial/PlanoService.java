package br.com.clairtonluz.bytecom.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.PlanoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class PlanoService implements Serializable {

    private static final long serialVersionUID = -8296012997453708684L;
    @Inject
    private PlanoJPA planoJPA;

    public List<Plano> findAll() {
        return planoJPA.buscarTodos();
    }

    public boolean planAvaliable(Plano plano) {
        Plano p = planoJPA.buscarPorNome(plano.getNome());
        return p == null || p.getId() == plano.getId();
    }

    public boolean isNotUsed(Plano plano) {
        return planoJPA.isNotUsed(plano);
    }

    public Plano buscarPorId(int id) {
        return planoJPA.buscarPorId(id);
    }

    public void remover(Plano plano) {
        planoJPA.remove(plano);
    }

    public Plano save(Plano plano) {
        return (Plano) planoJPA.save(plano);
    }
}
