package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Estado;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by clairtonluz on 01/01/16.
 */

public interface EstadoRepository extends CrudRepository<Estado, Integer> {

    Estado findOptionalByUf(String uf);
}
