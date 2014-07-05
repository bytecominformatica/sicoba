package net.servehttp.bytecom.persistence.entity.caixa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "header_lote")
public class HeaderLote implements Serializable {

	private static final long serialVersionUID = 1073955464009876576L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="numero_remessa_retorno")
	private int numeroRemessaRetorno;
	@Column(name="data_gravacao_remessa_retorno")
	private Date dataGravacaoRemessaRetorno;

	@ManyToOne
	@JoinColumn(name = "header_id")
	private Header header;
	@OneToMany(mappedBy="headerLote", cascade= CascadeType.ALL)
	private List<Registro> registros;
	@OneToOne(mappedBy="headerLote", cascade= CascadeType.ALL)
	private TrailerLote trailerLote;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumeroRemessaRetorno() {
		return numeroRemessaRetorno;
	}

	public void setNumeroRemessaRetorno(int numeroRemessaRetorno) {
		this.numeroRemessaRetorno = numeroRemessaRetorno;
	}

	public Date getDataGravacaoRemessaRetorno() {
		return dataGravacaoRemessaRetorno;
	}

	public void setDataGravacaoRemessaRetorno(Date dataGravacaoRemessaRetorno) {
		this.dataGravacaoRemessaRetorno = dataGravacaoRemessaRetorno;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public TrailerLote getTrailerLote() {
		return trailerLote;
	}

	public void setTrailerLote(TrailerLote trailerLote) {
		this.trailerLote = trailerLote;
	}
	
}
