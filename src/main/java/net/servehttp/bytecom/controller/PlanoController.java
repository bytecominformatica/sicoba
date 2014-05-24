package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Plano;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@Named
@javax.faces.view.ViewScoped
public class PlanoController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Plano> listPlanos;
	private Plano planoSelecionado;
	private Plano novoPlano = new Plano();
	@Inject
	private Util util;
	@Inject
	private GenericoJPA genericoJPA;
	
	public PlanoController() {
	}

	@PostConstruct
	public void load() {
		listPlanos = genericoJPA.buscarTodos(Plano.class);
		limpar();
		getParameters();
	}

	private void getParameters() {
		String planoId = util.getParameters("planoId");
		if (planoId != null && !planoId.isEmpty()) {
			planoSelecionado = genericoJPA.buscarPorId(Plano.class, Integer.parseInt(planoId));
		}
	}

	public void setSelecionado(Plano plano) {
		planoSelecionado = plano;
	}

	private void limpar() {
		planoSelecionado = null;
	}

	public List<Plano> getListPlanos() {
		return listPlanos;
	}

	public void setListPlanos(List<Plano> listPlanos) {
		this.listPlanos = listPlanos;
	}

	public Plano getPlanoSelecionado() {
		return planoSelecionado;
	}

	public void setPlanoSelecionado(Plano planoSelecionado) {
		this.planoSelecionado = planoSelecionado;
	}

	public Plano getNovoPlano() {
		return novoPlano;
	}

	public void setNovoPlano(Plano novoPlano) {
		this.novoPlano = novoPlano;
	}

	public String salvar() {
		String page = null;
		if (valida(novoPlano)) {
			genericoJPA.salvar(novoPlano);
			AlertaUtil.alerta("Plano adicionado com sucesso!");
			load();
			novoPlano = new Plano();
			page = "list";
		}
		return page;
	}

	private boolean valida(Plano plano) {
		boolean result = true;
		
		if (plano.getNome() == null || plano.getNome().isEmpty()) {
			result = false;
			AlertaUtil.alerta("Nome inválido!", AlertaUtil.ERROR);
		}

		return result;
	}

	public String atualizar() {
		genericoJPA.atualizar(planoSelecionado);
		load();
		AlertaUtil.alerta("Plano atualizado com sucesso!");
		return "list";
	}

	public String remover() {
		genericoJPA.remover(planoSelecionado);
		load();
		AlertaUtil.alerta("Plano removido com sucesso!");
		return "list";
	}

}
