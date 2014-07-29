package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Clairton
 */
@Entity
@Table(name = "equipamento")
public class Equipamento implements Serializable {
    
	public static final int STATUS_OK = 0;
	public static final int STATUS_DEFEITO = 1;
	
    public static final int TIPO_INSTALACAO = 0;
    public static final int TIPO_WIFI = 1;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descricao;
    @Size(min = 1, max = 30)
    private String marca;
    @Size(min = 1, max = 30)
    private String modelo;
    @Size(min = 1, max = 20)
    @Pattern(regexp="^[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}$", message="MAC inválido")
    private String mac;
    private int tipo;
    private int status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Equipamento() {
    }

    public Equipamento(Integer id) {
        this.setId(id);
    }

    public Equipamento(Integer id, String marca, String modelo, String mac, Date updatedAt) {
        this.setId(id);
        this.marca = marca;
        this.modelo = modelo;
        this.mac = mac;
        this.updatedAt = updatedAt;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getTIPO_INSTALACAO() {
        return TIPO_INSTALACAO;
    }

    public int getTIPO_WIFI() {
        return TIPO_WIFI;
    }
    
    public int getSTATUS_OK() {
		return STATUS_OK;
	}

	public int getSTATUS_DEFEITO() {
		return STATUS_DEFEITO;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
