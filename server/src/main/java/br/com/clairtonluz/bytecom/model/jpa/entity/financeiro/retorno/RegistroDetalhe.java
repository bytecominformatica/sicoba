package br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno;

import br.com.clairtonluz.bytecom.util.converter.date.LocalDatePersistenceConverter;
import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Registro Detalhe - Segmento U (Obrigatório - Retorno)
 */
@Entity
@Table(name = "registro_detalhe")
public class RegistroDetalhe extends EntityGeneric {

    private static final long serialVersionUID = 3727868029617370955L;

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
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataOcorrencia;
    @Column(name = "data_credito")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataCredito;
    @Column(name = "data_debito_tarifa")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataDebitoTarifa;

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

    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public LocalDate getDataCredito() {
        return dataCredito;
    }

    public void setDataCredito(LocalDate dataCredito) {
        this.dataCredito = dataCredito;
    }

    public LocalDate getDataDebitoTarifa() {
        return dataDebitoTarifa;
    }

    public void setDataDebitoTarifa(LocalDate dataDebitoTarifa) {
        this.dataDebitoTarifa = dataDebitoTarifa;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }
}
