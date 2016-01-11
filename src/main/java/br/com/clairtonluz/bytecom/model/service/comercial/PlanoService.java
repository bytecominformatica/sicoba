package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.PlanoRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
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

    @Transactional
    public Plano save(Plano plano) {
        return (Plano) planoRepository.save(plano);
    }

    @Transactional
    public void remover(Integer id) {
        Plano plano = planoRepository.findBy(id);
        planoRepository.remove(plano);
    }

}
