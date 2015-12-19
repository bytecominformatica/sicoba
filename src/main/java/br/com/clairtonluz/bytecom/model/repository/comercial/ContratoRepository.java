package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */

@Repository
public interface ContratoRepository extends EntityRepository<Contrato, Integer> {

    List<Contrato> findByDataInstalacaoBetween(LocalDate from, LocalDate to);
    Contrato findByCliente(Cliente cliente);
}
