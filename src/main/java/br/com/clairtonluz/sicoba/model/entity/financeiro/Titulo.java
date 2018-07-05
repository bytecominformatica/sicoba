package br.com.clairtonluz.sicoba.model.entity.financeiro;


import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author clairton
 */
@Entity
@Table(name = "titulo")
public class Titulo extends BaseEntity {

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;
    @Column(name = "data_ocorrencia")
    private LocalDate dataOcorrencia;
    private double valor;
    @Column(name = "valor_pago")
    private double valorPago;
    private double desconto;
    private double tarifa;
    @Enumerated
    private StatusTitulo status;
    private Integer modalidade;
    @Column(name = "numero_boleto")
    private Integer numeroBoleto;

    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private Cliente cliente;

    public Titulo() {
        status = StatusTitulo.PENDENTE;
        modalidade = 14;
    }

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

    public StatusTitulo getStatus() {
        return status;
    }

    public void setStatus(StatusTitulo status) {
        this.status = status;
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

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
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
}
