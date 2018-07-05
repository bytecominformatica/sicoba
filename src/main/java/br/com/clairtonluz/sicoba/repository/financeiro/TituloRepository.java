package br.com.clairtonluz.sicoba.repository.financeiro;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.model.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Integer> {

    List<Titulo> findByCliente_idOrderByDataVencimentoDesc(Integer clienteId);

    List<Titulo> findByNumeroBoletoBetween(Integer inicio, Integer fim);

    List<Titulo> findByStatusAndDataVencimentoLessThanAndCliente_statusNotOrderByDataVencimentoAsc(StatusTitulo statusTitulo, LocalDate date, StatusCliente statusCliente);

    Titulo findOptionalByNumeroBoleto(Integer numeroBoleto);

    List<Titulo> findByDataOcorrenciaBetween(LocalDate inicio, LocalDate fim);

    List<Titulo> findByDataVencimentoBetween(LocalDate inicio, LocalDate fim);

    List<Titulo> findByDataOcorrenciaBetweenAndStatus(LocalDate inicio, LocalDate fim, StatusTitulo status);

    List<Titulo> findByDataVencimentoBetweenAndStatus(LocalDate inicio, LocalDate fim, StatusTitulo status);

    List<Titulo> findByClienteAndStatusAndDataVencimentoGreaterThan(Cliente cliente, StatusTitulo status, LocalDate date);

    List<Titulo> findByIdIn(List<Integer> ids);
}
