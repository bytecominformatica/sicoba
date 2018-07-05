package br.com.clairtonluz.sicoba.repository.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by clairtonluz on 04/07/17.
 */
@Repository
public interface GerencianetAccountRepository extends JpaRepository<GerencianetAccount, Integer> {

}
