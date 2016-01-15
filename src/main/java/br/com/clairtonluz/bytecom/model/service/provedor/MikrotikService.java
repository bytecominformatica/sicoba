package br.com.clairtonluz.bytecom.model.service.provedor;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.bytecom.model.repository.provedor.MikrotikRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by clairtonluz on 07/11/15.
 */
public class MikrotikService implements Serializable {

    private static final long serialVersionUID = -6903933604712585280L;
    @Inject
    private MikrotikRepository mikrotikRepository;

    public List<Mikrotik> buscarTodos() {
        return mikrotikRepository.findAll();
    }

    @Transactional
    public Mikrotik save(Mikrotik mikrotik) {
        return mikrotikRepository.save(mikrotik);
    }

    @Transactional
    public void remove(Integer id) {
        Mikrotik mikrotik = buscarPorId(id);
        mikrotikRepository.remove(mikrotik);
    }

    public Mikrotik buscarPorId(Integer id) {
        return mikrotikRepository.findBy(id);
    }
}
