package br.com.clairtonluz.bytecom.model.jpa.entity.financeiro;


import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.persistence.*;
import java.util.Date;

/**
 * @author clairton
 */
@Entity
@Table(name = "mensalidade")
public class Titulo extends EntityGeneric {

    private static final long serialVersionUID = -8955481650524371350L;
    @Column(name = "data_vencimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVencimento;
    @Column(name = "data_ocorrencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOcorrencia;
    private double valor;
    @Column(name = "valor_pago")
    private double valorPago;
    private double desconto;
    private double tarifa;
    private String parcela;
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

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
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

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }


    public Integer getModalidade() {
        return modalidade;
    }

    public void setModalidade(Integer modalidade) {
        this.modalidade = modalidade;
    }
}
