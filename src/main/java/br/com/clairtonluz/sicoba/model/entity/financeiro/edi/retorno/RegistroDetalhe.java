package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Registro Detalhe - Segmento U (Obrigatório - Retorno)
 */
@Entity
@Table(name = "registro_detalhe")
public class RegistroDetalhe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "registro_detalhe_id_seq")
    @SequenceGenerator(name = "registro_detalhe_id_seq", sequenceName = "registro_detalhe_id_seq")
    private Integer id;
    @Column(name = "juros_multas_encargos")
    private double jurosMultasEncargos;
    private double desconto;
    private double abatimento;
    private double iof;
    @Column(name = "valor_pago")
    private double valorPago;
    @Column(name = "valor_liquido")
    private double valorLiquido;
    @Column(name = "data_ocorrencia")
    @Temporal(TemporalType.DATE)
    private Date dataOcorrencia;
    @Column(name = "data_credito")
    @Temporal(TemporalType.DATE)
    private Date dataCredito;
    @Column(name = "data_debito_tarifa")
    @Temporal(TemporalType.DATE)
    private Date dataDebitoTarifa;

    @OneToOne
    @JoinColumn(name = "registro_id")
    private Registro registro;

    public double getJurosMultasEncargos() {
        return jurosMultasEncargos;
    }

    public void setJurosMultasEncargos(double jurosMultasEncargos) {
        this.jurosMultasEncargos = jurosMultasEncargos;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getAbatimento() {
        return abatimento;
    }

    public void setAbatimento(double abatimento) {
        this.abatimento = abatimento;
    }

    public double getIof() {
        return iof;
    }

    public void setIof(double iof) {
        this.iof = iof;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public double getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(double valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public Date getDataCredito() {
        return dataCredito;
    }

    public void setDataCredito(Date dataCredito) {
        this.dataCredito = dataCredito;
    }

    public Date getDataDebitoTarifa() {
        return dataDebitoTarifa;
    }

    public void setDataDebitoTarifa(Date dataDebitoTarifa) {
        this.dataDebitoTarifa = dataDebitoTarifa;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
