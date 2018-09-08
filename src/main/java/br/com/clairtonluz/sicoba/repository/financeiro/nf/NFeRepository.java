package br.com.clairtonluz.sicoba.repository.financeiro.nf;

import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NFe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NFeRepository extends JpaRepository<NFe, Integer> {

}
