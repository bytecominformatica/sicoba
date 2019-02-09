package br.com.clairtonluz.sicoba.repository.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */

public interface ConexaoRepository extends JpaRepository<Conexao, Integer> {

    Conexao findOptionalByCliente(Cliente cliente);

    Conexao findOptionalByNome(String nome);

    Conexao findOptionalByIp(String ip);

    List<Conexao> findAllByOrderByNomeAsc();
}
