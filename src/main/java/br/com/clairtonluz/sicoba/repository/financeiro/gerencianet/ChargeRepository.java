package br.com.clairtonluz.sicoba.repository.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.model.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */


public interface ChargeRepository extends CrudRepository<Charge, Integer> {

    List<Charge> findByCarnet_id(Integer id);
}
