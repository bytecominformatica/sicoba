package br.com.clairtonluz.sicoba.repository.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno.Header;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by clairtonluz on 12/01/16.
 */


public interface HeaderRepository extends CrudRepository<Header, Integer> {

    List<Header> findBySequencial(Integer sequencial);
}
