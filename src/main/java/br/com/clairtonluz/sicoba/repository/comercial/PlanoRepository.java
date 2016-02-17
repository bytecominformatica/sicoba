package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by clairtonluz on 01/01/16.
 */
public interface PlanoRepository extends CrudRepository<Plano, Integer> {

    Plano findOptionalByNome(String nome);
}
