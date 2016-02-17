package br.com.clairtonluz.sicoba.service.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.sicoba.repository.provedor.MikrotikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by clairtonluz on 07/11/15.
 */
@Service
public class MikrotikService {

    @Autowired
    private MikrotikRepository mikrotikRepository;

    public Iterable<Mikrotik> buscarTodos() {
        return mikrotikRepository.findAll();
    }

    @Transactional
    public Mikrotik save(Mikrotik mikrotik) {
        return mikrotikRepository.save(mikrotik);
    }

    @Transactional
    public void remove(Integer id) {
        Mikrotik mikrotik = buscarPorId(id);
        mikrotikRepository.delete(mikrotik);
    }

    public Mikrotik buscarPorId(Integer id) {
        return mikrotikRepository.findOne(id);
    }
}
