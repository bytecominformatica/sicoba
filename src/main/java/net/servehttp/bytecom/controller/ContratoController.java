package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.EquipamentoJPA;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Cliente;
import net.servehttp.bytecom.persistence.entity.Contrato;
import net.servehttp.bytecom.persistence.entity.Equipamento;
import net.servehttp.bytecom.persistence.entity.Plano;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@ManagedBean
@ViewScoped
public class ContratoController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Contrato> listContratos;
	private List<Plano> listPlanos;
	private List<Equipamento> listEquipamentos;
	private List<Equipamento> listEquipamentosWifi;
	private Contrato contrato;
	private Cliente clienteSelecionado;
	private int clienteId;
	private int planoId;
	private int equipamentoId;
	private int equipamentoWifiId;
	@Inject
	private EquipamentoJPA equipamentoJPA;
	@Inject
	private Util util;
	@Inject
	private GenericoJPA genericoJPA;

	@PostConstruct
	public void load() {
		listPlanos = genericoJPA.buscarTodos(Plano.class);
		listEquipamentos = equipamentoJPA.buscaEquipamentosNaoUtilizados(
				Equipamento.TIPO_INSTALACAO, Equipamento.STATUS_OK);
		listEquipamentosWifi = equipamentoJPA.buscaEquipamentosNaoUtilizados(
				Equipamento.TIPO_WIFI, Equipamento.STATUS_OK);
		getParameters();
	}

	private void getParameters() {
		String clienteId = util.getParameters("clienteId");
		if (clienteId != null && !clienteId.isEmpty()) {
			clienteSelecionado = genericoJPA.buscarPorId(Cliente.class,
					Integer.parseInt(clienteId));
			if (clienteSelecionado.getContrato() == null) {
				Contrato c = new Contrato();
				c.setCliente(clienteSelecionado);
				c.setDataInstalacao(new Date());
				clienteSelecionado.setContrato(c);
			}

			if (clienteSelecionado.getContrato().getPlano() != null) {
				planoId = clienteSelecionado.getContrato().getPlano().getId();
			}

			Equipamento e = clienteSelecionado.getContrato().getEquipamento();
			if (e != null && !listEquipamentos.contains(e)) {
				equipamentoId = e.getId();
				listEquipamentos.add(e);
			}

			e = clienteSelecionado.getContrato().getEquipamentoWifi();
			if (e != null && !listEquipamentosWifi.contains(e)) {
				equipamentoWifiId = e.getId();
				System.out.println("ADICIONOU");
				listEquipamentosWifi.add(e);
			}
		}
	}

	public List<Equipamento> getListEquipamentosWifi() {
		return listEquipamentosWifi;
	}

	public void setListEquipamentosWifi(List<Equipamento> listEquipamentosWifi) {
		this.listEquipamentosWifi = listEquipamentosWifi;
	}

	public int getPlanoId() {
		return planoId;
	}

	public void setPlanoId(int planoId) {
		this.planoId = planoId;
		if (planoId > 0) {
			clienteSelecionado.getContrato().setPlano(
					genericoJPA.buscarPorId(Plano.class, planoId));
		}
	}

	public int getEquipamentoId() {
		return equipamentoId;
	}

	public void setEquipamentoId(int equipamentoId) {
		this.equipamentoId = equipamentoId;
		clienteSelecionado.getContrato().setEquipamento(
				buscaNaListaEquipamento(equipamentoId));
	}

	public int getEquipamentoWifiId() {
		return equipamentoWifiId;
	}

	public void setEquipamentoWifiId(int equipamentoWifiId) {
		this.equipamentoWifiId = equipamentoWifiId;
		clienteSelecionado.getContrato().setEquipamentoWifi(
				buscaNaListaWifi(equipamentoWifiId));
	}

	private Equipamento buscaNaListaWifi(int id) {
		for (Equipamento e : listEquipamentosWifi) {
			if (e.getId() == id) {
				return e;
			}
		}
		return null;
	}

	private Equipamento buscaNaListaEquipamento(int id) {
		for (Equipamento e : listEquipamentos) {
			if (e.getId() == id) {
				return e;
			}
		}
		return null;
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

	public String salvar() {
		genericoJPA.salvar(contrato);
		load();
		AlertaUtil.alerta("Contrato adicionado com sucesso!");
		return "edit";
	}

	public String atualizar() {
		String page = null;
		contrato = clienteSelecionado.getContrato();
		if (contrato != null) {
			if (contrato.getId() > 0) {
				genericoJPA.atualizar(contrato);
				load();
				AlertaUtil.alerta("Contrato atualizado com sucesso!");
				page = "edit";
			} else {
				page = salvar();
			}
		} else {
			System.out.println("contrato null");
			AlertaUtil.alerta("Contrato NULL!");

		}
		return page;
	}

	public void remover() {
		genericoJPA.remover(contrato);
		load();
		AlertaUtil.alerta("Contrato removido com sucesso!");
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

}
