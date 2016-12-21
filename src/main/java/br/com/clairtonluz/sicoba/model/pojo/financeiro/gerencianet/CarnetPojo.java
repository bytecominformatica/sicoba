package br.com.clairtonluz.sicoba.model.pojo.financeiro.gerencianet;

import java.util.Date;

/**
 * Created by clairtonluz on 28/11/16.
 */
public class CarnetPojo {

    private Integer clienteId;
    private Integer quantidadeParcela;
    private Double valor;
    private Double desconto;
    private Date dataInicio;

    public CarnetPojo() {
        this.desconto = 0d;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getQuantidadeParcela() {
        return quantidadeParcela;
    }

    public void setQuantidadeParcela(Integer quantidadeParcela) {
        this.quantidadeParcela = quantidadeParcela;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
}
