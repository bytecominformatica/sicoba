package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by clairtonluz on 01/01/16.
 */

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    Estado findOptionalByUf(String uf);
}
