package br.com.clairtonluz.sicoba.repository.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.Cedente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by clairtonluz on 19/09/16.
 */
@Repository
public interface CedenteRepository extends CrudRepository<Cedente, Integer> {

}
