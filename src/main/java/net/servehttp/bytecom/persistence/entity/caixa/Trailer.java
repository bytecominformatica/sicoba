package net.servehttp.bytecom.persistence.entity.caixa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trailer")
public class Trailer implements Serializable {

	private static final long serialVersionUID = -4486342321787747170L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int quantidadeLotes;
	private int quantidadeRegistros;

	@OneToOne
	@JoinColumn(name = "header_id")
	private Header header;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantidadeLotes() {
		return quantidadeLotes;
	}

	public void setQuantidadeLotes(int quantidadeLotes) {
		this.quantidadeLotes = quantidadeLotes;
	}

	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}

	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

}
