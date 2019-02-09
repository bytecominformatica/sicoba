package br.com.clairtonluz.sicoba.repository.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Integer> {

    <T> List<T> findByCarnet_idOrderByExpireAtDesc(Integer id);

    <T> List<T> findByCliente_idOrderByExpireAtDesc(Integer clienteId);

    <T> T findOptionalByCarnet_idAndParcel(Integer carnetId, Integer parcel);

    <T> List<T> findByCarnet_id(Integer carnetId);

    <T> T findOptionalByChargeId(int chargeId);

    <T> List<T> findByCarnetIsNullAndStatusNot(StatusCharge paid);
//
//    @Query("select c from Charge c " +
//            "where c.expireAt < :date " +
//            "and c.status <> 'PAID' " +
//            "and c.status <> 'CANCELED' " +
//            "and c.cliente.status <> 2 " +
//            "order by c.expireAt asc ")
//    <T> List<T> overdue(@Param("date") LocalDate date);

    <T> List<T> findByExpireAtLessThanAndStatusNotInAndCliente_statusNotOrderByExpireAt(
            LocalDate date, List<StatusCharge> statusChargeList, StatusCliente statusCliente);

    @Query("SELECT c FROM Charge c where c.cliente.id = :clientId and (c.status = 'UNPAID' OR c.expireAt between :begin and :finish) order by c.expireAt asc ")
    <T> List<T> findCurrentByClientAndDate(@Param("clientId") Integer clientId, @Param("begin") LocalDate begin, @Param("finish") LocalDate finish);

    <T> List<T> findByPaidAtBetween(LocalDateTime start, LocalDateTime end);

    <T> List<T> findByPaidAtBetweenAndStatus(LocalDateTime start, LocalDateTime end, StatusCharge status);

    <T> List<T> findByPaidAtBetweenAndGerencianetAccount_id(LocalDateTime start, LocalDateTime end, Integer gerencianetAccountId);

    <T> List<T> findByPaidAtBetweenAndStatusAndGerencianetAccount_id(LocalDateTime start, LocalDateTime end, StatusCharge status, Integer gerencianetAccountId);

    <T> List<T> findByExpireAtBetween(LocalDate start, LocalDate end);

    <T> List<T> findByExpireAtBetweenAndStatus(LocalDate start, LocalDate end, StatusCharge status);

    <T> List<T> findByExpireAtBetweenAndGerencianetAccount_id(LocalDate start, LocalDate end, Integer gerencianetAccountId);

    <T> List<T> findByExpireAtBetweenAndStatusAndGerencianetAccount_id(LocalDate start, LocalDate end, StatusCharge status, Integer gerencianetAccountId);

    <T> List<T> findByClienteAndStatusAndExpireAtGreaterThan(Cliente cliente, StatusCharge waiting, LocalDate date);

    <T> T findOptionalById(Integer id);
}
