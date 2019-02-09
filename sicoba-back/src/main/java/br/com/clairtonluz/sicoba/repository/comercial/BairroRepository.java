package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by clairtonluz on 15/11/15.
 */

public interface BairroRepository extends JpaRepository<Bairro, Integer> {

    Bairro findOptionalByNome(String nome);
}
