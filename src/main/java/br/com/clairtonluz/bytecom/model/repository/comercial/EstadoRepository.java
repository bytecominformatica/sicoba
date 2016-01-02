package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Estado;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Created by clairtonluz on 01/01/16.
 */
@Repository
public interface EstadoRepository extends EntityRepository<Estado, Integer> {

    Estado findOptionalByUf(String uf);
}
