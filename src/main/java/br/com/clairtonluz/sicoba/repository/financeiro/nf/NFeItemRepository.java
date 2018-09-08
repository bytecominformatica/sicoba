package br.com.clairtonluz.sicoba.repository.financeiro.nf;

import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NFeItemRepository extends JpaRepository<NfeItem, Integer> {

}
