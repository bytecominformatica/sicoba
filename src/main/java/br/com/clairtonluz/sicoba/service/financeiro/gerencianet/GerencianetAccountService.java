package br.com.clairtonluz.sicoba.service.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.GerencianetAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by clairton on 09/11/16.
 */
@Service
public class GerencianetAccountService {
    @Autowired
    private GerencianetAccountRepository gerencianetAccountRepository;

    public Iterable<GerencianetAccount> findAll() {
        return gerencianetAccountRepository.findAll();
    }

    public GerencianetAccount findById(Integer id) {
        return gerencianetAccountRepository.findOne(id);
    }

    public GerencianetAccount save(GerencianetAccount gerencianetAccount) {
        return gerencianetAccountRepository.save(gerencianetAccount);
    }

    public void remover(Integer id) {
        gerencianetAccountRepository.delete(id);
    }
}
