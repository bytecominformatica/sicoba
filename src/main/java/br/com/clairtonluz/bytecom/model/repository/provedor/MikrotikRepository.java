package br.com.clairtonluz.bytecom.model.repository.provedor;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Mikrotik;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Created by clairtonluz on 15/11/15.
 */
@Repository
public interface MikrotikRepository extends EntityRepository<Mikrotik, Integer> {
}
