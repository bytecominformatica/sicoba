package br.com.clairtonluz.bytecom.model.repository.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno.Header;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * Created by clairtonluz on 12/01/16.
 */

@Repository
public interface HeaderRepository extends EntityRepository<Header, Integer> {

    List<Header> findBySequencial(Integer sequencial);
}
