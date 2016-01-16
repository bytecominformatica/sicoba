package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.entity.comercial.Cidade;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */
@Repository
public interface CidadeRepository extends EntityRepository<Cidade, Integer> {

    @Query("select c from Cidade c order by c.nome asc")
    List<Cidade> findAllOrderByNome();


    Cidade findOptionalByNomeAndEstado_uf(String nome, String nomeEstado);
}
