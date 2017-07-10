package br.com.clairtonluz.sicoba.repository.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */

@Repository
public interface ChargeRepository extends CrudRepository<Charge, Integer> {

    List<Charge> findByCarnet_idOrderByExpireAtDesc(Integer id);

    List<Charge> findByCliente_idOrderByExpireAtDesc(Integer clienteId);

    Charge findOptionalByCarnet_idAndParcel(Integer carnetId, Integer parcel);

    List<Charge> findByCarnet_id(Integer carnetId);

    Charge findOptionalByChargeId(int chargeId);

    List<Charge> findByCarnetIsNullAndStatusNot(StatusCharge paid);

    @Query("select c from Charge c where c.expireAt < :date and c.status <> 'PAID' and c.status <> 'CANCELED' and c.cliente.status <> 2 order by c.expireAt asc ")
    List<Charge> overdue(@Param("date") Date date);

    @Query("SELECT c FROM Charge c where c.cliente.id = :clientId and (c.status = 'UNPAID' OR c.expireAt between :begin and :finish) order by c.expireAt asc ")
    List<Charge> findCurrentByClientAndDate(@Param("clientId") Integer clientId, @Param("begin") Date begin, @Param("finish") Date finish);

    List<Charge> findByPaidAtBetween(Date start, Date end);

    List<Charge> findByPaidAtBetweenAndStatus(Date start, Date end, StatusCharge status);

    List<Charge> findByExpireAtBetween(Date start, Date end);

    List<Charge> findByExpireAtBetweenAndStatus(Date start, Date end, StatusCharge status);
}
