package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "acesso")
public class Acesso extends EntityGeneric implements Serializable {

	private static final long serialVersionUID = 4021634951278920635L;
	@Transient
	public final static int INATIVO = 0;
	@Transient
	public final static int ATIVO = 1;
	@Transient
	public final static int CANCELADO = 2;

	@Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "IP inválido")
	@Size(min = 1, max = 20)
	private String ip;
	@Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "Mascara inválido")
	@Size(min = 1, max = 20)
	private String mascara;
	@Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "Gateway inválido")
	@Size(min = 1, max = 20)
	private String gateway;
	@Pattern(regexp = "^[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}$", message = "MAC inválido")
	@Size(min = 1, max = 20)
	private String mac;
	private int status;
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Cliente cliente;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getINATIVO() {
		return INATIVO;
	}

	public int getATIVO() {
		return ATIVO;
	}

	public int getCANCELADO() {
		return CANCELADO;
	}

	public String getStatusFormatado() {
		switch (status) {
		case 0:
			return "INATIVO";
		case 1:
			return "ATIVO";
		case 2:
			return "CANCELADO";
		default:
			return "";
		}
	}

}
