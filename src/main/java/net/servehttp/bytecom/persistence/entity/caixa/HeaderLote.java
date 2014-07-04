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
@Table(name = "header_lote")
public class HeaderLote implements Serializable {

	private static final long serialVersionUID = 1073955464009876576L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;


	@OneToOne
	@JoinColumn(name = "header_id")
	private Header header;

}
