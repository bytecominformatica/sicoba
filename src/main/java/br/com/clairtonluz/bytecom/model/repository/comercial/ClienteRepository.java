package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Created by clairtonluz on 15/11/15.
 */
@Repository
public interface ClienteRepository extends EntityRepository<Cliente, Integer> {

}
