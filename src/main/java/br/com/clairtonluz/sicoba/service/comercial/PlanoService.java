package br.com.clairtonluz.sicoba.service.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.comercial.PlanoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;
    @Autowired
    private ContratoRepository contratoRepository;

    public Iterable<Plano> buscarTodos() {
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
        return planoRepository.findOne(id);
    }

    @Transactional
    public Plano save(Plano plano) {
        return planoRepository.save(plano);
    }

    @Transactional
    public void remover(Integer id) {
        Plano plano = planoRepository.findOne(id);
        planoRepository.delete(plano);
    }

}
