package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    List<Cliente> findByStatus(StatusCliente status);

    Cliente findOptionalByEmail(String email);

    Cliente findOptionalByCpfCnpj(String cpfCnpj);

    Cliente findOptionalByRg(String rg);

    List<Cliente> findByUpdatedAtGreaterThan(Date data);

    List<Cliente> findByNomeLike(String nome);

    @Query("select c from Cliente c where c.status <> 2 and c.id not in(select DISTINCT(m.cliente.id) from Titulo m where m.dataVencimento > :date)")
    List<Cliente> findBySemTitulosDepoisDe(@Param("date") Date date);

    List<Cliente> findByStatusAndUpdatedAtGreaterThanOrderByUpdatedAtDesc(StatusCliente status, Date data);
}
