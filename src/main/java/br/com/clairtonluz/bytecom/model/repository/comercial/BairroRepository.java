package br.com.clairtonluz.bytecom.model.repository.comercial;

import br.com.clairtonluz.bytecom.model.entity.comercial.Bairro;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */
@Repository
public interface BairroRepository extends EntityRepository<Bairro, Integer> {

    List<Bairro> findByCidade_id(Integer cidadeId);

    Bairro findOptionalByNome(String nome);
}
