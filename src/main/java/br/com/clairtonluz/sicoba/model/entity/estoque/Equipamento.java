package br.com.clairtonluz.sicoba.model.entity.estoque;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Clairton
 */
@Entity
@Table(name = "equipamento")
public class Equipamento extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "equipamento_id_seq")
    @SequenceGenerator(name = "equipamento_id_seq", sequenceName = "equipamento_id_seq")
    private Integer id;
    private String descricao;
    @Size(min = 1, max = 30)
    private String marca;
    @Size(min = 1, max = 30)
    private String modelo;
    @Pattern(
            regexp = "^[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}$",
            message = "MAC inválido")
    private String mac;
    @Enumerated
    private TipoEquipamento tipo;
    @Enumerated
    private StatusEquipamento status;

    public Equipamento() {
    }

    public Equipamento(Integer id) {
        this.setId(id);
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca != null ? marca.toUpperCase() : marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo != null ? modelo.toUpperCase() : modelo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac != null ? mac.toUpperCase() : mac;
    }

    @Override
    public String toString() {
        return modelo + " - " + mac;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoEquipamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEquipamento tipo) {
        this.tipo = tipo;
    }

    public StatusEquipamento getStatus() {
        return status;
    }

    public void setStatus(StatusEquipamento status) {
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
