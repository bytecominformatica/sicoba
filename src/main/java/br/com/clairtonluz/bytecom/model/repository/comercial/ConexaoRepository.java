package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */
@Repository
public interface ConexaoRepository extends EntityRepository<Conexao, Integer> {

    Conexao findOptionalByCliente(Cliente cliente);

    Conexao findOptionalByNome(String nome);

    Conexao findOptionalByIp(String ip);

    List<Conexao> findAllOrderByNomeAsc();
}
