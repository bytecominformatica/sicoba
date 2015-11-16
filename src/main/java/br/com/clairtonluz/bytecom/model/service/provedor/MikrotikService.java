package br.com.clairtonluz.bytecom.model.service.provedor;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.bytecom.model.repository.provedor.MikrotikRepository;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by clairtonluz on 07/11/15.
 */
public class MikrotikService implements Serializable {

    private static final long serialVersionUID = -6903933604712585280L;
    @Inject
    private MikrotikRepository mikrotikRepository;

    public List<Mikrotik> findAll() {
        return mikrotikRepository.findAll();
    }

    public Mikrotik save(Mikrotik mikrotik) {
        return mikrotikRepository.save(mikrotik);
    }

    public void remove(Mikrotik mikrotik) {
        mikrotikRepository.remove(mikrotik);
    }
}
