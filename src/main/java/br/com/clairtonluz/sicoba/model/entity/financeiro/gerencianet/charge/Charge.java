package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge;


import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author clairton
 */
@Entity
@Table(name = "charge")
public class Charge extends BaseEntity {

    public static final String VALID_PAYMENT = "Pagamento válido";
    public static final String LATE_PAYMENT_WITHOUT_INTEREST = "Pagamento atrasado sem juros";
    public static final String INVALID_DISCOUNT_PAYMENT = "Pagamento com desconto inválido";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "charge_id_seq")
    @SequenceGenerator(name = "charge_id_seq", sequenceName = "charge_id_seq")
    private Integer id;

    @NotNull
    private Double value;
    @Column(name = "paid_value")
    private Double paidValue;
    private Double discount;
    private Integer parcel;
    @Column(name = "token_notification")
    private String tokenNotification;
    @Column(name = "last_notification")
    private Integer lastNotification;
    private String url;
    @Column(name = "payment_url")
    private String paymentUrl;
    private String barcode;
    @Size(min = 3, max = 80, message = "A mensagem deve conter entre 3 e 80 caracteres")
    private String message;
    @NotNull
    @Size(min = 1, max = 255, message = "A mensagem deve conter entre 1 e 255 caracteres")
    private String description;
    @Column(name = "manual_payment")
    private Boolean manualPayment;

    @Column(name = "charge_id")
    private Integer chargeId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCharge status;
    @NotNull
    @Column(name = "expire_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireAt;
    @Column(name = "paid_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paidAt;
    @Enumerated(EnumType.STRING)
    private PaymentType payment;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "carnet_id")
    private Carnet carnet;

    public String verifyPayment() {
        String status = VALID_PAYMENT;

        if (isInterestFreePayment()) {
            if (isLatePayment()) {
                status = LATE_PAYMENT_WITHOUT_INTEREST;
            }
        }

        if (status.equals(VALID_PAYMENT)) {
            if (isInvalidDiscount())
                status = INVALID_DISCOUNT_PAYMENT;
        }

        return status;
    }

    private boolean isInvalidDiscount() {
        Double discount = getDiscount() != null ? getDiscount() : 0d;
        Double correctValue = getValue() - discount;
        return getPaidValue() < correctValue;
    }

    private boolean isLatePayment() {
        boolean late = false;
        LocalDate expireAt = DateUtil.toLocalDate(getExpireAt());
        LocalDate paidAt = DateUtil.toLocalDate(getPaidAt());
        long diference = ChronoUnit.DAYS.between(expireAt, paidAt);

        if (diference > 0) {
            late = !isLateForTheWeekend(paidAt, diference);
        }

        return late;
    }

    private boolean isLateForTheWeekend(LocalDate paidAt, long diference) {
        return diference <= 2 && paidAt.getDayOfWeek().equals(DayOfWeek.MONDAY);
    }

    private boolean isInterestFreePayment() {
        return getPaidValue() <= getValue();
    }

    public boolean isCancelable() {
        return !StatusCharge.PAID.equals(status);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        this.message = StringUtil.isEmpty(message) ? null : message;
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

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
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

    public Carnet getCarnet() {
        return carnet;
    }

    public void setCarnet(Carnet carnet) {
        this.carnet = carnet;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPaidValue() {
        return paidValue;
    }

    public void setPaidValue(Double paidValue) {
        this.paidValue = paidValue;
    }

    public Integer getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(Integer lastNotification) {
        this.lastNotification = lastNotification;
    }

    public String getTokenNotification() {
        return tokenNotification;
    }

    public void setTokenNotification(String tokenNotification) {
        this.tokenNotification = tokenNotification;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public Boolean getManualPayment() {
        return manualPayment;
    }

    public void setManualPayment(Boolean manualPayment) {
        this.manualPayment = manualPayment;
    }

}
