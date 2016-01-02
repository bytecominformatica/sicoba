package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Created by clairtonluz on 01/01/16.
 */

@Repository
public interface PlanoRepository extends EntityRepository<Plano, Integer> {

    Plano findOptionalByNome(String nome);
}
