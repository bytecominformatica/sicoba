package br.com.clairtonluz.bytecom.model.jpa.entity.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "cedente")
public class Cedente extends EntityGeneric {

    private static final long serialVersionUID = -2529079451437071047L;

    private String nome;

    private int codigo;

    @Column(name = "digito_verificador")
    private int digitoVerificador;

    @Column(name = "codigo_operacao")
    private int codigoOperacao;

    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @Column(name = "numero_agencia")
    private int numeroAgencia;

    @Column(name = "digito_agencia")
    private int digitoAgencia;

    @Column(name = "numero_conta")
    private int numeroConta;

    @Column(name = "digito_conta")
    private int digitoConta;

    private int prazo;

    private double multa;

    private double juros;

    public Cedente() {
        createdAt = new Date();
    }

    public int getCodigoOperacao() {
        return this.codigoOperacao;
    }

    public void setCodigoOperacao(int codigoOperacao) {
        this.codigoOperacao = codigoOperacao;
    }

    public String getCpfCnpj() {
        return this.cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public int getDigitoAgencia() {
        return this.digitoAgencia;
    }

    public void setDigitoAgencia(int digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public int getDigitoConta() {
        return this.digitoConta;
    }

    public void setDigitoConta(int digitoConta) {
        this.digitoConta = digitoConta;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroAgencia() {
        return this.numeroAgencia;
    }

    public void setNumeroAgencia(int numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public int getNumeroConta() {
        return this.numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(int digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public double getJuros() {
        return juros;
    }

    public void setJuros(double juros) {
        this.juros = juros;
    }

}
