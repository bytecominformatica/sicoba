package net.servehttp.bytecom.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cidade database table.
 * 
 */
@Entity
@Table(name="cidade")
@NamedQuery(name="Cidade.findAll", query="SELECT c FROM Cidade c")
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nome;

	@OneToMany(mappedBy="cidade", fetch=FetchType.EAGER)
	private List<Bairro> bairros;

	@ManyToOne(fetch=FetchType.EAGER)
	private Estado estado;

	public Cidade() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Bairro> getBairros() {
		return this.bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	public Bairro addBairro(Bairro bairro) {
		getBairros().add(bairro);
		bairro.setCidade(this);

		return bairro;
	}

	public Bairro removeBairro(Bairro bairro) {
		getBairros().remove(bairro);
		bairro.setCidade(null);

		return bairro;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String toString(){
		return nome;
	}
}