package br.com.clairtonluz.sicoba.model.entity.financeiro;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "cedente")
public class Cedente extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cedente_id_seq")
    @SequenceGenerator(name = "cedente_id_seq", sequenceName = "cedente_id_seq")
    private Integer id;
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
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

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

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
