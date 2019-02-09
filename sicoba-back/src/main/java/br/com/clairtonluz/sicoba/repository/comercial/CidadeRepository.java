package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by clairtonluz on 15/11/15.
 */

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    Cidade findOptionalByNomeAndEstado_uf(String nome, String nomeEstado);
}
