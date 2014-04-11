package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.EquipamentoJPA;
import net.servehttp.bytecom.persistence.entity.Equipamento;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@ManagedBean
@ViewScoped
public class EquipamentoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8291411734476446041L;
	private List<Equipamento> listEquipamentos;
	private Equipamento equipamentoSelecionado = new Equipamento();
	private Equipamento novoEquipamento = new Equipamento();
	@Inject
	private EquipamentoJPA equipamentoJPA;
	private String equipamentoId;
	private String page;

	public EquipamentoController() {
	}

	@PostConstruct
	public void load() {
		listEquipamentos = equipamentoJPA.buscaTodosOsEquipamentos();
	}

	public List<Equipamento> getListEquipamentos() {
		return listEquipamentos;
	}

	public void setListEquipamentos(List<Equipamento> listEquipamentos) {
		this.listEquipamentos = listEquipamentos;
	}

	public Equipamento getEquipamentoSelecionado() {

		return equipamentoSelecionado;
	}

	public void setEquipamentoSelecionado(Equipamento equipamentoSelecionado) {
		this.equipamentoSelecionado = equipamentoSelecionado;
	}

	public Equipamento getNovoEquipamento() {
		return novoEquipamento;
	}

	public void setNovoEquipamento(Equipamento novoEquipamento) {
		this.novoEquipamento = novoEquipamento;
	}

	public String salvar() {
		page = null;
		if (!equipamentoJPA.existMAC(novoEquipamento.getMac())) {
			equipamentoJPA.salvar(novoEquipamento);
			AlertaUtil.alerta("Equipamento adicionado com sucesso!");
			load();
			page = "list";
		} else {
			AlertaUtil.alerta("MAC já Cadastrado", AlertaUtil.ERROR);
		}
		return page;
	}

	public String atualizar() {
		page = null;
		try {
			equipamentoJPA.atualizar(equipamentoSelecionado);
			load();
			AlertaUtil.alerta("Equipamento atualizado com sucesso!");
			page = "list";
		} catch (EJBException e) {
			if (equipamentoJPA.existMAC(equipamentoSelecionado.getMac())) {
				AlertaUtil.alerta("MAC já Cadastrado", AlertaUtil.ERROR);
			} else {
				throw e;
			}
		}
		return page;
	}

	public String remover() {
		equipamentoJPA.remover(equipamentoSelecionado);
		load();
		AlertaUtil.alerta("Equipamento removido com sucesso!");
		return "list";
	}

	public String getEquipamentoId() {
		return equipamentoId;
	}

	public void setEquipamentoId(String equipamentoId) {
		this.equipamentoId = equipamentoId;
	}

}
