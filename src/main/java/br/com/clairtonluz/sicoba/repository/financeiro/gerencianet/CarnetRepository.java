package br.com.clairtonluz.sicoba.repository.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */


public interface CarnetRepository extends CrudRepository<Carnet, Integer> {

    List<Carnet> findByCliente_id(Integer clienteId);
}
