package br.com.clairtonluz.bytecom.model.repository.estoque;

import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.Equipamento;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */

@Repository
public interface EquipamentoRepository extends EntityRepository<Equipamento, Integer> {

    @Query("select e from Equipamento e where e.tipo = 0 and e.status = 0 and e.id not in(select c.equipamento.id from Contrato c where c.equipamento.id is not null)")
    List<Equipamento> findByDisponiveisInstalacao();

    @Query("select e from Equipamento e where e.tipo = 1 and e.status = 0 and e.id not in(select c.equipamentoWifi.id from Contrato c where c.equipamentoWifi.id is not null)")
    List<Equipamento> findByDisponiveisWifi();

    Equipamento findOptionalByMac(String mac);
}
