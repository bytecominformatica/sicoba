package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by clairton on 21/12/16.
 */
@Entity
@Table(name = "carnet")
public class Carnet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "charge_id_seq")
    @SequenceGenerator(name = "charge_id_seq", sequenceName = "charge_id_seq")
    private Integer id;

    @Column(name = "carnet_id")
    private Integer carnetId;
    @NotNull
    @Size(min = 1, max = 255, message = "A mensagem deve conter entre 1 e 255 caracteres")
    private String description;
    @Size(min = 3, max = 80, message = "A mensagem deve conter entre 3 e 80 caracteres")
    private String message;
    private String link;
    private String cover;
    @Min(2)
    @Max(12)
    @NotNull
    private Integer repeats;
    @NotNull
    @Column(name = "split_items")
    private Boolean splitItems;
    @NotNull
    private Double value;
    private Double discount;
    @Column(name = "token_notification")
    private String tokenNotification;
    @Column(name = "last_notification")
    private Integer lastNotification;
    @NotNull
    @Column(name = "first_pay")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstPay;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCarnet status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @NotNull
    private Cliente cliente;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarnetId() {
        return carnetId;
    }

    public void setCarnetId(Integer carnetId) {
        this.carnetId = carnetId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public StatusCarnet getStatus() {
        return status;
    }

    public void setStatus(StatusCarnet status) {
        this.status = status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getRepeats() {
        return repeats;
    }

    public void setRepeats(Integer repeats) {
        this.repeats = repeats;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getSplitItems() {
        return splitItems;
    }

    public void setSplitItems(Boolean splitItems) {
        this.splitItems = splitItems;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getFirstPay() {
        return firstPay;
    }

    public void setFirstPay(Date firstPay) {
        this.firstPay = firstPay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Double getDiscountSplit() {
        Double discount = this.discount;
        if (splitItems && discount != null && discount > 0) {
            discount = discount / repeats;
        }
        return discount;
    }
}
