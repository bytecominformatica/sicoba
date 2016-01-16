package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.entity.comercial.StatusCliente;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.Date;
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

    List<Cliente> findByUpdatedAtGreaterThan(Date data);

    List<Cliente> findByNomeLike(String nome);

    @Query("select c from Cliente c where c.status <> 2 and c.id not in(select DISTINCT(m.cliente.id) from Titulo m where m.dataVencimento > ?1)")
    List<Cliente> findBySemTitulosDepoisDe(Date date);
}
