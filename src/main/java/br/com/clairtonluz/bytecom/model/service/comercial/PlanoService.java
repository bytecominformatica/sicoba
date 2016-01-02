package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.PlanoRepository;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class PlanoService implements Serializable {

    private static final long serialVersionUID = -8296012997453708684L;
    @Inject
    private PlanoRepository planoRepository;
    @Inject
    private ContratoRepository contratoRepository;

    public List<Plano> buscarTodos() {
        return planoRepository.findAll();
    }

    public boolean planAvaliable(Plano plano) {
        Plano p = planoRepository.findOptionalByNome(plano.getNome());
        return p == null || p.getId() == plano.getId();
    }

    public boolean isNotUsed(Plano plano) {
        return contratoRepository.findByPlano(plano).isEmpty();
    }

    public Plano buscarPorId(Integer id) {
        return planoRepository.findBy(id);
    }

    public void remover(Plano plano) {
        planoRepository.remove(plano);
    }

    public Plano save(Plano plano) {
        return (Plano) planoRepository.save(plano);
    }

}
