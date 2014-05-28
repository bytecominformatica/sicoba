package net.servehttp.bytecom.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 *
 * @author clairton
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message="nome é obrigatório")
    @Size(max=255, message="nome tamanho máximo 255 caracteres")
    private String nome;
    private String rg;
    
    @Column(name="cpf_cnpj")
    private String cpfCnpj;
    @Email(message="Email inválido")
    private String email;
    @Column(name = "fone_titular")
    @Max(value=10, message="Telefone deve possuir 10 digitos ex: 9999999999")
    private String foneTitular;
    private String contato;
    @Max(value=10, message="Telefone deve possuir 10 digitos ex: 9999999999")
    @Column(name = "fone_contato")
    private String foneContato;
    
    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Calendar createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Calendar updateAt;

    @OneToOne(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Acesso acesso;
    @OneToOne(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Contrato contrato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Mensalidade> mensalidades;

    @Transient
    private boolean online;

    public Cliente(){
		this.endereco = new Endereco();
	}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public Calendar getUpdatedAt() {
        return updateAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Calendar updatedAt) {
        this.updateAt = updatedAt;
    }

    public String getFoneTitular() {
        return foneTitular;
    }

    public void setFoneTitular(String foneTitular) {
        this.foneTitular = foneTitular;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getFoneContato() {
        return foneContato;
    }

    public void setFoneContato(String foneContato) {
        this.foneContato = foneContato;
    }

    public int getStatus() {
        if (acesso != null) {
            return acesso.getStatus();
        }
        return -1;
    }

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
	
}
