package net.servehttp.bytecom.persistence.jpa.entity.financeiro;


import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;
import net.servehttp.bytecom.util.converter.date.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
 */
@Entity
@Table(name = "mensalidade")
public class Mensalidade extends EntityGeneric implements Serializable {

    private static final long serialVersionUID = -8955481650524371350L;
    @Column(name = "data_vencimento")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataVencimento;
    @Column(name = "data_ocorrencia")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataOcorrencia;
    private double valor;
    @Column(name = "valor_pago")
    private double valorPago;
    private double desconto;
    @Column(name = "baixa_manual")
    private boolean baixaManual;
    @Column(name = "modalidade")
    private Integer modalidade;
    @Column(name = "numero_boleto")
    private Integer numeroBoleto;

    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private Cliente cliente;

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getNumeroBoleto() {
        return numeroBoleto;
    }

    public void setNumeroBoleto(Integer numeroBoleto) {
        this.numeroBoleto = numeroBoleto;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Integer getModalidade() {
        return modalidade;
    }

    public void setModalidade(Integer modalidade) {
        this.modalidade = modalidade;
    }

    public boolean isBaixaManual() {
        return baixaManual;
    }

    public void setBaixaManual(boolean baixaManual) {
        this.baixaManual = baixaManual;
    }
}
