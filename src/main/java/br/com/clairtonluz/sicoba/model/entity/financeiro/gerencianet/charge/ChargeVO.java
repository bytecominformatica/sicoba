package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge;


import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItem;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ChargeVO extends BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Integer id;
    private Double value;
    private Double paidValue;
    private Double discount;
    private Integer parcel;
    private String tokenNotification;
    private Integer lastNotification;
    private String url;
    private String paymentUrl;
    private String barcode;
    private String message;
    private String description;
    private Boolean manualPayment;
    private Integer chargeId;
    private StatusCharge status;
    private LocalDate expireAt;
    private LocalDateTime paidAt;
    private PaymentType payment;
    private Cliente cliente;
    private GerencianetAccount gerencianetAccount;
    private Carnet carnet;
    private NfeItem nfeItem;

    public ChargeVO() {
    }

    public ChargeVO(Charge charge) {
        this.createdAt = charge.getCreatedAt();
        this.updatedAt = charge.getUpdatedAt();
        this.createdBy = charge.getCreatedBy();
        this.updatedBy = charge.getUpdatedBy();
        this.id = charge.getId();
        this.value = charge.getValue();
        this.paidValue = charge.getPaidValue();
        this.discount = charge.getDiscount();
        this.parcel = charge.getParcel();
        this.tokenNotification = charge.getTokenNotification();
        this.lastNotification = charge.getLastNotification();
        this.url = charge.getUrl();
        this.paymentUrl = charge.getPaymentUrl();
        this.barcode = charge.getBarcode();
        this.message = charge.getMessage();
        this.description = charge.getDescription();
        this.manualPayment = charge.getManualPayment();
        this.chargeId = charge.getChargeId();
        this.status = charge.getStatus();
        this.expireAt = charge.getExpireAt();
        this.paidAt = charge.getPaidAt();
        this.payment = charge.getPayment();
        this.cliente = charge.getCliente();
        this.gerencianetAccount = charge.getGerencianetAccount();
        this.carnet = charge.getCarnet();
        this.nfeItem = charge.getNfeItem();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getPaidValue() {
        return paidValue;
    }

    public void setPaidValue(Double paidValue) {
        this.paidValue = paidValue;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getParcel() {
        return parcel;
    }

    public void setParcel(Integer parcel) {
        this.parcel = parcel;
    }

    public String getTokenNotification() {
        return tokenNotification;
    }

    public void setTokenNotification(String tokenNotification) {
        this.tokenNotification = tokenNotification;
    }

    public Integer getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(Integer lastNotification) {
        this.lastNotification = lastNotification;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getManualPayment() {
        return manualPayment;
    }

    public void setManualPayment(Boolean manualPayment) {
        this.manualPayment = manualPayment;
    }

    public Integer getChargeId() {
        return chargeId;
    }

    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }

    public StatusCharge getStatus() {
        return status;
    }

    public void setStatus(StatusCharge status) {
        this.status = status;
    }

    public LocalDate getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDate expireAt) {
        this.expireAt = expireAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public PaymentType getPayment() {
        return payment;
    }

    public void setPayment(PaymentType payment) {
        this.payment = payment;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public GerencianetAccount getGerencianetAccount() {
        return gerencianetAccount;
    }

    public void setGerencianetAccount(GerencianetAccount gerencianetAccount) {
        this.gerencianetAccount = gerencianetAccount;
    }

    public Carnet getCarnet() {
        return carnet;
    }

    public void setCarnet(Carnet carnet) {
        this.carnet = carnet;
    }

    public NfeItem getNfeItem() {
        return nfeItem;
    }

    public void setNfeItem(NfeItem nfeItem) {
        this.nfeItem = nfeItem;
    }
}
