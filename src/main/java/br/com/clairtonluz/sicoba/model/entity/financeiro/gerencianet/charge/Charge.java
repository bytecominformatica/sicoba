package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge;


import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author clairton
 */
@Entity
@Table(name = "charge")
public class Charge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "charge_id_seq")
    @SequenceGenerator(name = "charge_id_seq", sequenceName = "charge_id_seq")
    private Integer id;

    @NotNull
    private Double value;
    private Double discount;
    private Integer parcel;
    private String url;
    @Column(name = "payment_url")
    private String paymentUrl;
    private String barcode;
    @Size(min = 3, max = 80, message = "A mensagem deve conter entre 3 e 80 caracteres")
    private String message;
    @NotNull
    @Size(min = 1, max = 255, message = "A mensagem deve conter entre 1 e 255 caracteres")
    private String description;

    @Column(name = "charge_id")
    private Integer chargeId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCharge status;
    @NotNull
    @Column(name = "expire_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireAt;
    @Enumerated(EnumType.STRING)
    private PaymentType payment;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "carnet_id")
    private Carnet carnet;

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
}
