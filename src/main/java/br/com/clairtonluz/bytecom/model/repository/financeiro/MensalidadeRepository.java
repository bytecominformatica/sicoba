package br.com.clairtonluz.bytecom.model.repository.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */

@Repository
public interface MensalidadeRepository extends EntityRepository<Mensalidade, Integer> {

    List<Mensalidade> findByCliente_idOrderByDataVencimentoAsc(Integer clienteId);

    List<Mensalidade> findByNumeroBoletoBetweenAndModalidade(Integer inicio, Integer fim, Integer modalidade);

    List<Mensalidade> findByStatusAndDataVencimentoLessThanOrderByDataVencimentoAsc(StatusMensalidade status, Date date);

    Mensalidade findOptionalByNumeroBoletoAndModalidade(Integer numeroBoleto, Integer modalidade);
}
