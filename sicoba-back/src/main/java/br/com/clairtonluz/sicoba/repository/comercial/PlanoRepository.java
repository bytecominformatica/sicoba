package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by clairtonluz on 01/01/16.
 */
public interface PlanoRepository extends JpaRepository<Plano, Integer> {

    Plano findOptionalByNome(String nome);
}
