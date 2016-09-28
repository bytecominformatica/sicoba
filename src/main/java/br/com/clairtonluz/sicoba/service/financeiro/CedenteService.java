package br.com.clairtonluz.sicoba.service.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.Cedente;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.CedenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CedenteService {

    @Autowired
    private CedenteRepository cedenteRepository;
    @Autowired
    private ContratoRepository contratoRepository;

    public Iterable<Cedente> buscarTodos() {
        return cedenteRepository.findAll();
    }

    public Cedente buscarPorId(Integer id) {
        return cedenteRepository.findOne(id);
    }

    @Transactional
    public Cedente save(Cedente cedente) {
        return cedenteRepository.save(cedente);
    }

    @Transactional
    public void remover(Integer id) {
        Cedente cedente = cedenteRepository.findOne(id);
        cedenteRepository.delete(cedente);
    }
}
