package br.com.clairtonluz.sicoba.model.entity.financeiro.nf;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "nfe")
public class NFe extends BaseEntity {
    public static final int MODELO_21 = 21;
    public static final int MODELO_22 = 22;

    @Column(name = "cliente_id")
    private Integer clienteId;
    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairo;
    private String cidade;
    private String uf;
    private String cep;
    private String cnpj;
    private String ie;
    private String cpf;
    private String rg;
    @Column(name = "dia_vencimento")
    private Integer diaDeVencimento;
    private int modelo;
    private int cfop;
    private String telefone;
    private String email;
    @Column(name = "codigo_consumidor")
    private Integer codigoConsumidor;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_assinante")
    private TipoAssinante tipoAssinante;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_utilizacao")
    private TipoUtilizacao tipoUtilizacao;
    @Column(name = "data_emissao")
    private LocalDate dataEmissao;
    @Column(name = "data_prestacao")
    private LocalDate dataPrestacao;
    private String observacao; // null
    @Column(name = "codigo_municipio")
    private String codigoMunicipio; // null

    private List<NfeItem> itens;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Optional<String> getComplemento() {
        return Optional.ofNullable(complemento);
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairo() {
        return bairo;
    }

    public void setBairo(String bairo) {
        this.bairo = bairo;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Optional<String> getCnpj() {
        return Optional.ofNullable(cnpj);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Optional<String> getIe() {
        return Optional.ofNullable(ie);
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public Optional<String> getCpf() {
        return Optional.ofNullable(cpf);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Optional<String> getRg() {
        return Optional.ofNullable(rg);
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public int getCfop() {
        return cfop;
    }

    public void setCfop(int cfop) {
        this.cfop = cfop;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCodigoConsumidor() {
        return codigoConsumidor;
    }

    public void setCodigoConsumidor(Integer codigoConsumidor) {
        this.codigoConsumidor = codigoConsumidor;
    }

    public TipoAssinante getTipoAssinante() {
        return tipoAssinante;
    }

    public void setTipoAssinante(TipoAssinante tipoAssinante) {
        this.tipoAssinante = tipoAssinante;
    }

    public TipoUtilizacao getTipoUtilizacao() {
        return tipoUtilizacao;
    }

    public void setTipoUtilizacao(TipoUtilizacao tipoUtilizacao) {
        this.tipoUtilizacao = tipoUtilizacao;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataPrestacao() {
        return dataPrestacao;
    }

    public void setDataPrestacao(LocalDate dataPrestacao) {
        this.dataPrestacao = dataPrestacao;
    }

    public Optional<String> getObservacao() {
        return Optional.ofNullable(observacao);
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Optional<String> getCodigoMunicipio() {
        return Optional.ofNullable(codigoMunicipio);
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public List<NfeItem> getItens() {
        return itens;
    }

    public void setItens(List<NfeItem> itens) {
        this.itens = itens;
    }

    public Integer getDiaDeVencimento() {
        return diaDeVencimento;
    }

    public void setDiaDeVencimento(Integer diaDeVencimento) {
        this.diaDeVencimento = diaDeVencimento;
    }

}
