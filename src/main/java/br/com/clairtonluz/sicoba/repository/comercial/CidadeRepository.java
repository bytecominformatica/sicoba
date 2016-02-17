package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cidade;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by clairtonluz on 15/11/15.
 */

public interface CidadeRepository extends CrudRepository<Cidade, Integer> {

    Cidade findOptionalByNomeAndEstado_uf(String nome, String nomeEstado);
}
