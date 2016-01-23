package br.com.clairtonluz.sicoba.model.pojo.financeiro;

import java.util.Date;

/**
 * Created by clairtonluz on 10/01/16.
 */
public class Carne {

    Integer clienteId;
    Integer modalidade;
    Integer boletoInicio;
    Integer boletoFim;
    Double valor;
    Double desconto;
    Date dataInicio;

    public Carne() {
        this.desconto = 0d;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getModalidade() {
        return modalidade;
    }

    public void setModalidade(Integer modalidade) {
        this.modalidade = modalidade;
    }

    public Integer getBoletoInicio() {
        return boletoInicio;
    }

    public void setBoletoInicio(Integer boletoInicio) {
        this.boletoInicio = boletoInicio;
    }

    public Integer getBoletoFim() {
        return boletoFim;
    }

    public void setBoletoFim(Integer boletoFim) {
        this.boletoFim = boletoFim;
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
