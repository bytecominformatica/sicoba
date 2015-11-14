package br.com.clairtonluz.bytecom.controller.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.comercial.ContractService;
import br.com.clairtonluz.bytecom.model.service.comercial.PlanoService;
import br.com.clairtonluz.bytecom.model.service.estoque.EquipamentoService;
import br.com.clairtonluz.bytecom.model.service.provedor.IConnectionControl;
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
    private List<Contrato> listContratos;
    private List<Plano> listPlanos;
    private List<Equipamento> listEquipamentos;
    private List<Equipamento> listEquipamentosWifi;

    private Cliente cliente;
    private int clienteId;
    @Inject
    private PlanoService planoBusiness;
    @Inject
    private EquipamentoService equipamentoBusiness;
    @Inject
    private ClienteService clienteService;
    @Inject
    private ContractService contratoService;
    @Inject
    private IConnectionControl connectionControl;

    @PostConstruct
    public void load() {
        listPlanos = planoBusiness.findAll();
        listEquipamentos = equipamentoBusiness
                .buscarEquipamentosInstalacaoNaoUtilizados();
        listEquipamentosWifi = equipamentoBusiness
                .buscarEquipamentosWifiNaoUtilizados();
        getParameters();
    }

    private void getParameters() {
        String clienteId = WebUtil.getParameters("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            setCliente(clienteService.buscarPorId(Integer.parseInt(clienteId)));
            if (getCliente().getContrato() == null) {
                gerarNovoContrato();
            }

            Equipamento e = getCliente().getContrato().getEquipamento();
            if (e != null && !listEquipamentos.contains(e)) {
                listEquipamentos.add(e);
            }

            e = getCliente().getContrato().getEquipamentoWifi();
            if (e != null && !listEquipamentosWifi.contains(e)) {
                listEquipamentosWifi.add(e);
            }
        }
    }

    private void gerarNovoContrato() {
        Contrato c = new Contrato();
        c.setCliente(getCliente());
        c.setDataInstalacao(LocalDate.now());
        getCliente().setContrato(c);
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

    public List<Contrato> getListContratos() {
        return listContratos;
    }

    public void setListContratos(List<Contrato> listContratos) {
        this.listContratos = listContratos;
    }

    public String salvar() throws Exception {
        contratoService.save(cliente.getContrato());
        if (cliente.getConexao() != null) {
            cliente.getStatus()
                    .atualizarConexao(cliente, connectionControl);
        }
        load();
        AlertaUtil.info("salvo com sucesso!");

        return "edit";
    }

    public void remover() {
        contratoService.remove(cliente.getContrato());
        gerarNovoContrato();
        load();
        AlertaUtil.info("Contrato removido com sucesso!");
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
