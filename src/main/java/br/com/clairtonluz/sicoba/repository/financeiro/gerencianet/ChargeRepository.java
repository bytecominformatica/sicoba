package br.com.clairtonluz.sicoba.repository.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Integer> {

    List<Charge> findByCarnet_idOrderByExpireAtDesc(Integer id);

    List<Charge> findByCliente_idOrderByExpireAtDesc(Integer clienteId);

    Charge findOptionalByCarnet_idAndParcel(Integer carnetId, Integer parcel);

    List<Charge> findByCarnet_id(Integer carnetId);

    Charge findOptionalByChargeId(int chargeId);

    List<Charge> findByCarnetIsNullAndStatusNot(StatusCharge paid);

    @Query("select c from Charge c where c.expireAt < :date and c.status <> 'PAID' and c.status <> 'CANCELED' and c.cliente.status <> 2 order by c.expireAt asc ")
    List<Charge> overdue(@Param("date") LocalDate date);

    @Query("SELECT c FROM Charge c where c.cliente.id = :clientId and (c.status = 'UNPAID' OR c.expireAt between :begin and :finish) order by c.expireAt asc ")
    List<Charge> findCurrentByClientAndDate(@Param("clientId") Integer clientId, @Param("begin") LocalDate begin, @Param("finish") LocalDate finish);

    List<Charge> findByPaidAtBetween(LocalDate start, LocalDate end);

    List<Charge> findByPaidAtBetweenAndStatus(LocalDate start, LocalDate end, StatusCharge status);

    List<Charge> findByPaidAtBetweenAndGerencianetAccount_id(LocalDate start, LocalDate end, Integer gerencianetAccountId);

    List<Charge> findByPaidAtBetweenAndStatusAndGerencianetAccount_id(LocalDate start, LocalDate end, StatusCharge status, Integer gerencianetAccountId);

    List<Charge> findByExpireAtBetween(LocalDate start, LocalDate end);

    List<Charge> findByExpireAtBetweenAndStatus(LocalDate start, LocalDate end, StatusCharge status);

    List<Charge> findByExpireAtBetweenAndGerencianetAccount_id(LocalDate start, LocalDate end, Integer gerencianetAccountId);

    List<Charge> findByExpireAtBetweenAndStatusAndGerencianetAccount_id(LocalDate start, LocalDate end, StatusCharge status, Integer gerencianetAccountId);
}
