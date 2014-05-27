package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Despesa;
import net.servehttp.bytecom.util.AlertaUtil;

@Named
@ViewScoped
public class DespesaController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Despesa> listDespesa;
	private Despesa despesaSelecionada;
	private Despesa novaDespesa = new Despesa();
	@Inject
	private GenericoJPA genericoJPA;
	
	public DespesaController(){}
	
	@PostConstruct
	public void load(){
		listDespesa = genericoJPA.buscarTodos(Despesa.class);
	}
	
	public List<Despesa> getListDespesa() {
		return listDespesa;
	}

	public void setListDespesa(List<Despesa> listDespesa) {
		this.listDespesa = listDespesa;
	}

	public Despesa getDespesaSelecionada() {
		return despesaSelecionada;
	}

	public void setDespesaSelecionada(Despesa despesaSelecionada) {
		this.despesaSelecionada = despesaSelecionada;
	}

	public Despesa getNovaDespesa() {
		return novaDespesa;
	}

	public void setNovaDespesa(Despesa novaDespesa) {
		this.novaDespesa = novaDespesa;
	}

	public void salvar(){
		genericoJPA.salvar(novaDespesa);
		AlertaUtil.alerta("Despesa gravada com sucesso!");
	}
	
	public void atualizar(){
		genericoJPA.atualizar(despesaSelecionada);
	}
	
	public void remover(){
		System.out.println("Removendo");
		List<Despesa> despesas = genericoJPA.buscarTodos("select d from Despesa d where d.despesa.id = ?1", 
				despesaSelecionada.getId(), Despesa.class);
		if(despesas.isEmpty()){
			genericoJPA.remover(despesaSelecionada);
			load();
			AlertaUtil.alerta("Removido com sucesso!");
			
		}
	}
	
}
