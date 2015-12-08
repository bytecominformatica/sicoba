package br.com.clairtonluz.bytecom.controller.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;
import br.com.clairtonluz.bytecom.model.service.comercial.PlanoService;
import br.com.clairtonluz.bytecom.model.service.estoque.EquipamentoService;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class ContratoController implements Serializable {

    private static final long serialVersionUID = -5226446405193705169L;

    private List<Plano> listPlanos;
    private List<Equipamento> listEquipamentos;
    private List<Equipamento> listEquipamentosWifi;

    private Cliente cliente;
    private Contrato contrato;
    @Inject
    private PlanoService planoService;
    @Inject
    private EquipamentoService equipamentoService;
    @Inject
    private ClienteService clienteService;
    @Inject
    private ContratoService contratoService;

    @PostConstruct
    public void load() {
        listPlanos = planoService.findAll();
        listEquipamentos = equipamentoService.buscarEquipamentosInstalacaoNaoUtilizados();
        listEquipamentosWifi = equipamentoService.buscarEquipamentosWifiNaoUtilizados();
        getParameters();
    }

    public String salvar() throws Exception {
        contratoService.salvar(contrato);
        load();
        AlertaUtil.info("salvo com sucesso!");

        return "edit";
    }

    public void remover() {
        contratoService.remover(contrato);
        gerarNovoContrato();
        load();
        AlertaUtil.info("Contrato removido com sucesso!");
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clienteService.buscarPorId(Integer.parseInt(clienteId));
            contrato = contratoService.buscarPorCliente(cliente);
            if (contrato == null) {
                gerarNovoContrato();
            } else {

                Equipamento e = contrato.getEquipamento();
                if (e != null && !listEquipamentos.contains(e)) {
                    listEquipamentos.add(e);
                }

                e = contrato.getEquipamentoWifi();
                if (e != null && !listEquipamentosWifi.contains(e)) {
                    listEquipamentosWifi.add(e);
                }
            }
        }
    }

    private void gerarNovoContrato() {
        contrato = new Contrato();
        contrato.setCliente(cliente);
        contrato.setDataInstalacao(LocalDate.now());
    }

    public List<Equipamento> getListEquipamentosWifi() {
        return listEquipamentosWifi;
    }

    public void setListEquipamentosWifi(List<Equipamento> listEquipamentosWifi) {
        this.listEquipamentosWifi = listEquipamentosWifi;
    }

    public List<Equipamento> getListEquipamentos() {
        return listEquipamentos;
    }

    public void setListEquipamentos(List<Equipamento> listEquipamentos) {
        this.listEquipamentos = listEquipamentos;
    }

    public List<Plano> getListPlanos() {
        return listPlanos;
    }

    public void setListPlanos(List<Plano> listPlanos) {
        this.listPlanos = listPlanos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
