package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import org.springframework.data.jpa.repository.Query;
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

    @Query("select c from Contrato c where c.equipamento.id = ?1 or c.equipamentoWifi.id = ?1")
    Contrato findByEquipamento(Integer equipamentoId);
}
