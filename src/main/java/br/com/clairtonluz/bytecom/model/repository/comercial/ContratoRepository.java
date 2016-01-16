package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.entity.comercial.Plano;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */

@Repository
public interface ContratoRepository extends EntityRepository<Contrato, Integer> {

    List<Contrato> findByDataInstalacaoBetween(Date from, Date to);

    Contrato findOptionalByCliente_id(Integer clienteId);

    List<Contrato> findByPlano(Plano plano);
}
