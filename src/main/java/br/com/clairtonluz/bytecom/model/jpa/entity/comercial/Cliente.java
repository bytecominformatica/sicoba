package br.com.clairtonluz.bytecom.model.jpa.entity.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.util.annotation.CpfCnpj;
import br.com.clairtonluz.bytecom.util.converter.date.LocalDatePersistenceConverter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author clairton
 */
@Entity
@Table(name = "cliente")
public class Cliente extends EntityGeneric {

    private static final long serialVersionUID = -4600863330316226671L;
    @NotNull(message = "nome é obrigatório")
    @Size(max = 255, message = "nome tamanho máximo 255 caracteres")
    private String nome;
    private String rg;
    @Enumerated
    private StatusCliente status;

    @Column(name = "cpf_cnpj")
    @CpfCnpj
    private String cpfCnpj;
    @Column(name = "dt_nascimento")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataNascimento;
    @Email(message = "Email inválido")
    private String email;
    @NotNull(message = "Fone titular é obrigatório")
    @Column(name = "fone_titular")
    private String foneTitular;
    private String contato;
    @Column(name = "fone_contato")
    private String foneContato;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Conexao conexao;

    @OneToOne(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Contrato contrato;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Mensalidade> mensalidades;

    @Transient
    private boolean online;

    public Cliente() {
        this.createdAt = LocalDateTime.now();
        this.endereco = new Endereco();
        this.status = StatusCliente.ATIVO;
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
        this.nome = nome != null ? nome.toUpperCase() : nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg != null && rg.isEmpty() ? null : rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null && email.isEmpty() ? null : email;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
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
        this.contato = contato != null ? contato.toUpperCase() : contato;
    }

    public String getFoneContato() {
        return foneContato;
    }

    public void setFoneContato(String foneContato) {
        this.foneContato = foneContato;
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
        this.cpfCnpj = cpfCnpj != null && cpfCnpj.isEmpty() ? null : cpfCnpj;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public StatusCliente getStatus() {
        return status;
    }

    public void setStatus(StatusCliente status) {
        this.status = status;
    }

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }

}
