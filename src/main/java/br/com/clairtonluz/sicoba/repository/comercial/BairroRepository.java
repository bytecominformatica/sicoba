package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Bairro;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */

public interface BairroRepository extends CrudRepository<Bairro, Integer> {

    List<Bairro> findByCidade_id(Integer cidadeId);

    Bairro findOptionalByNome(String nome);
}
