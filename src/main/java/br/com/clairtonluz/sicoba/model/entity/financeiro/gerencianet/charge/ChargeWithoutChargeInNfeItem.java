package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge;


import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntityGets;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItemWithoutCharge;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ChargeWithoutChargeInNfeItem extends BaseEntityGets {

    public Double getValue();
    public Double getDiscount();
    public Integer getParcel();
    public String getUrl();
    public String getBarcode();
    public String getMessage();
    public Integer getChargeId();
    public StatusCharge getStatus();
    public LocalDate getExpireAt();
    public PaymentType getPayment();
    public Cliente getCliente();
    public Carnet getCarnet();
    public String getPaymentUrl();
    public String getDescription();
    public Double getPaidValue();
    public Integer getLastNotification();
    public String getTokenNotification();
    public LocalDateTime getPaidAt();
    public Boolean getManualPayment();
    public GerencianetAccount getGerencianetAccount();
    public NfeItemWithoutCharge getNfeItem();
}
