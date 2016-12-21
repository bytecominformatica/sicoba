package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;

/**
 * Created by clairton on 21/12/16.
 */
@Entity
@Table(name = "charge")
public class Carnet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "charge_id_seq")
    @SequenceGenerator(name = "charge_id_seq", sequenceName = "charge_id_seq")
    private Integer id;

    @Column(name = "carnet_id")
    private Integer carnetId;
    private String link;
    private String cover;
    @Enumerated(EnumType.STRING)
    private StatusCarnet status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
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
}
