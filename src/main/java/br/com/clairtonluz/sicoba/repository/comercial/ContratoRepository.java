package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */
public interface ContratoRepository extends CrudRepository<Contrato, Integer> {

    List<Contrato> findByDataInstalacaoBetween(Date from, Date to);

    Contrato findOptionalByCliente_id(Integer clienteId);

    List<Contrato> findByPlano(Plano plano);

    Contrato findOptionalByEquipamento_id(Integer equipamentoId);
}
