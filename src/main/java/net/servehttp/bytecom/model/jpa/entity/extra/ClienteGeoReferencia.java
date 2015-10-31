package net.servehttp.bytecom.model.jpa.entity.extra;

import net.servehttp.bytecom.model.jpa.entity.comercial.Cliente;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Felipe W. M. Martins
 */
@Entity
@Table(name = "cliente_georeferencia")
public class ClienteGeoReferencia extends EntityGeneric implements Serializable {

    private static final long serialVersionUID = -7721922565148704991L;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "lat")
    private Double latitude;

    @Column(name = "lng")
    private Double longitude;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
