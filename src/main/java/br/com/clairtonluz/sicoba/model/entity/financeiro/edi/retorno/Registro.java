package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Registro Detalhe - Segmento T (Obrigatório - Retorno)
 */
@Entity
@Table(name = "registro")
public class Registro extends BaseEntity {

    public static final int ENTRADA_CONFIRMADA = 2;
    public static final int ENTRADA_REJEITADA = 3;
    public static final int LIQUIDACAO = 6;
    public static final int BAIXA = 9;
    public static final int CONFIRMACAO_ALTERACAO_VENCIMENTO = 9;
    public static final int TARIFA = 28;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "registro_id_seq")
    @SequenceGenerator(name = "registro_id_seq", sequenceName = "registro_id_seq")
    private Integer id;
    @Column(name = "modalidade_nosso_numero")
    private int modalidadeNossoNumero;
    @Column(name = "nosso_numero")
    private int nossoNumero;
    @Column(name = "numero_documento")
    private String numeroDocumento;
    @Temporal(TemporalType.DATE)
    private Date vencimento;
    @Column(name = "valor_titulo")
    private double valorTitulo;
    @Column(name = "valor_tarifa")
    private double valorTarifa;
    @Column(name = "banco_recebedor")
    private String bancoRecebedor;
    @Column(name = "agencia_recebedor")
    private String agenciaRecebedor;
    @Column(name = "digito_verificador_recebedor")
    private String digitoVerificadorRecebedor;
    @Column(name = "uso_da_empresa")
    private String usoDaEmpresa;
    @Column(name = "codigo_movimento")
    private int codigoMovimento;

    @ManyToOne
    @JoinColumn(name = "header_lote_id")
    private HeaderLote headerLote;

    @OneToOne(mappedBy = "registro", cascade = CascadeType.ALL)
    private RegistroDetalhe registroDetalhe;

    public HeaderLote getHeaderLote() {
        return headerLote;
    }

    public void setHeaderLote(HeaderLote headerLote) {
        this.headerLote = headerLote;
    }

    public int getModalidadeNossoNumero() {
        return modalidadeNossoNumero;
    }

    public void setModalidadeNossoNumero(int modalidadeNossoNumero) {
        this.modalidadeNossoNumero = modalidadeNossoNumero;
    }

    public int getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(int nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public double getValorTitulo() {
        return valorTitulo;
    }

    public void setValorTitulo(double valorTitulo) {
        this.valorTitulo = valorTitulo;
    }

    public double getValorTarifa() {
        return valorTarifa;
    }

    public void setValorTarifa(double valorTarifa) {
        this.valorTarifa = valorTarifa;
    }

    public RegistroDetalhe getRegistroDetalhe() {
        return registroDetalhe;
    }

    public void setRegistroDetalhe(RegistroDetalhe registroDetalhe) {
        this.registroDetalhe = registroDetalhe;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getBancoRecebedor() {
        return bancoRecebedor;
    }

    public void setBancoRecebedor(String bancoRecebedor) {
        this.bancoRecebedor = bancoRecebedor;
    }

    public String getAgenciaRecebedor() {
        return agenciaRecebedor;
    }

    public void setAgenciaRecebedor(String agenciaRecebedor) {
        this.agenciaRecebedor = agenciaRecebedor;
    }

    public String getDigitoVerificadorRecebedor() {
        return digitoVerificadorRecebedor;
    }

    public void setDigitoVerificadorRecebedor(String digitoVerificadorRecebedor) {
        this.digitoVerificadorRecebedor = digitoVerificadorRecebedor;
    }

    public String getUsoDaEmpresa() {
        return usoDaEmpresa;
    }

    public void setUsoDaEmpresa(String usoDaEmpresa) {
        this.usoDaEmpresa = usoDaEmpresa;
    }

    public int getCodigoMovimento() {
        return codigoMovimento;
    }

    public void setCodigoMovimento(int codigoMovimento) {
        this.codigoMovimento = codigoMovimento;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
