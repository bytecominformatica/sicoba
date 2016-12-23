package br.com.clairtonluz.sicoba.repository.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno.Header;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by clairtonluz on 12/01/16.
 */

@Repository
public interface HeaderRepository extends CrudRepository<Header, Integer> {

    List<Header> findBySequencial(Integer sequencial);
}
