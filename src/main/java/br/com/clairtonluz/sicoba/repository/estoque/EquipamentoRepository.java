package br.com.clairtonluz.sicoba.repository.estoque;

import br.com.clairtonluz.sicoba.model.entity.estoque.Equipamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by clairtonluz on 15/11/15.
 */


public interface EquipamentoRepository extends CrudRepository<Equipamento, Integer> {

    @Query("select e from Equipamento e where e.tipo = 0 and e.status = 0 and e.id not in(select c.equipamento.id from Contrato c where c.equipamento.id is not null)")
    List<Equipamento> findByDisponiveisInstalacao();

    @Query("select e from Equipamento e where e.tipo = 1 and e.status = 0 and e.id not in(select c.equipamentoWifi.id from Contrato c where c.equipamentoWifi.id is not null)")
    List<Equipamento> findByDisponiveisWifi();

    Equipamento findOptionalByMac(String mac);
}
