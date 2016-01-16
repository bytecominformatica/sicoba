package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.entity.comercial.Conexao;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

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
