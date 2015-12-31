package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */
@Repository
public interface ClienteRepository extends EntityRepository<Cliente, Integer> {

    List<Cliente> findByStatus(StatusCliente status);

    Cliente findOptionalByEmail(String email);

    Cliente findOptionalByCpfCnpj(String cpfCnpj);

    Cliente findOptionalByRg(String rg);

    List<Cliente> findByUpdatedAtGreaterThan(LocalDateTime data);

    List<Cliente> findByNomeLike(String nome);
}
