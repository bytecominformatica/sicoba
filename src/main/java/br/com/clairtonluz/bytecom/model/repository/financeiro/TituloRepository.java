package br.com.clairtonluz.bytecom.model.repository.financeiro;

import br.com.clairtonluz.bytecom.model.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.bytecom.model.entity.financeiro.Titulo;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */

@Repository
public interface TituloRepository extends EntityRepository<Titulo, Integer> {

    List<Titulo> findByCliente_idOrderByDataVencimentoAsc(Integer clienteId);

    List<Titulo> findByNumeroBoletoBetween(Integer inicio, Integer fim);

    List<Titulo> findByStatusAndDataVencimentoLessThanOrderByDataVencimentoAsc(StatusTitulo status, Date date);

    Titulo findOptionalByNumeroBoleto(Integer numeroBoleto);

    List<Titulo> findByDataOcorrenciaBetween(Date inicio, Date fim);

    List<Titulo> findByDataVencimentoBetween(Date inicio, Date fim);

    List<Titulo> findByDataOcorrenciaBetweenAndStatus(Date inicio, Date fim, StatusTitulo status);

    List<Titulo> findByDataVencimentoBetweenAndStatus(Date inicio, Date fim, StatusTitulo status);


}
