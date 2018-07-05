package br.com.clairtonluz.sicoba.repository.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno.Header;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by clairtonluz on 12/01/16.
 */

@Repository
public interface HeaderRepository extends JpaRepository<Header, Integer> {

    List<Header> findBySequencial(Integer sequencial);
}
