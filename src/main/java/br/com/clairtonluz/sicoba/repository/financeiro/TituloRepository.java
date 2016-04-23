package br.com.clairtonluz.sicoba.repository.financeiro;

import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.model.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */


public interface TituloRepository extends CrudRepository<Titulo, Integer> {

    List<Titulo> findByCliente_idOrderByDataVencimentoAsc(Integer clienteId);

    List<Titulo> findByNumeroBoletoBetween(Integer inicio, Integer fim);

    List<Titulo> findByStatusAndDataVencimentoLessThanAndCliente_statusNotOrderByDataVencimentoAsc(StatusTitulo statusTitulo, Date date, StatusCliente statusCliente);

    Titulo findOptionalByNumeroBoleto(Integer numeroBoleto);

    List<Titulo> findByDataOcorrenciaBetween(Date inicio, Date fim);

    List<Titulo> findByDataVencimentoBetween(Date inicio, Date fim);

    List<Titulo> findByDataOcorrenciaBetweenAndStatus(Date inicio, Date fim, StatusTitulo status);

    List<Titulo> findByDataVencimentoBetweenAndStatus(Date inicio, Date fim, StatusTitulo status);


}
