package br.com.clairtonluz.sicoba.reports.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ContratoReportTest {

    private ContratoReport contratoReport;
    private Contrato contrato;

    @Before
    public void setUp() {
        contratoReport = new ContratoReport();
        Cliente cliente = criarCliente();
        Plano plano = new Plano();
        plano.setNome("1MB");
        plano.setValor(65d);
        contrato = new Contrato();
        contrato.setCliente(cliente);
        contrato.setDataInstalacao(LocalDateTime.now());
        contrato.setPlano(plano);
        contrato.setVencimento(25);
    }

    @Test
    public void contratoPDF() {
        contratoReport.contratoPDF(contrato);
    }

    private Cliente criarCliente() {
        Cidade cidade = new Cidade();
        cidade.setNome("Canoas");
        Bairro bairro = new Bairro();
        bairro.setNome("Guajuviras");
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Quadra Z Três");
        endereco.setNumero("400");
        endereco.setCep("92440240");
        endereco.setBairro(bairro);
        Cliente cliente = new Cliente();
        cliente.setNome("Renan Enrico Isaac Carvalho");
        cliente.setCpfCnpj("87895860348");
        cliente.setRg("386917462");
        cliente.setEndereco(endereco);
        return cliente;
    }

}