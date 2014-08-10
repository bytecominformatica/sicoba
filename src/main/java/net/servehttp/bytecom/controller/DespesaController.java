package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Despesa;
import net.servehttp.bytecom.persistence.entity.cadastro.Fornecedor;
import net.servehttp.bytecom.util.AlertaUtil;

@Named
@ViewScoped
public class DespesaController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Despesa> listDespesa;
	private List<Fornecedor> listFornecedor;
	private int fornecedorId;
	private Despesa despesaSelecionada;
	private Despesa novaDespesa = new Despesa();
	
	@Inject
	private Util util;
	@Inject
	private GenericoJPA genericoJPA;
	
	public DespesaController(){}
	
	@PostConstruct
	public void load(){
		listDespesa = genericoJPA.buscarTodos(Despesa.class);
		setListFornecedor(genericoJPA.buscarTodos(Fornecedor.class));
		limpar();
		getParameters();
	}
	
	public String salvar(){
	  String page = null;
	  if(valida(novaDespesa)){
	    novaDespesa.setFornecedor(genericoJPA.findById(Fornecedor.class, fornecedorId));
        genericoJPA.salvar(novaDespesa);
        AlertaUtil.alerta("Despesa gravada com sucesso!");
        load();
        novaDespesa = new Despesa();
        page = "list";
	  }
	  return page;
	}
	
	public void atualizar(){
		genericoJPA.atualizar(despesaSelecionada);
		load();
	}
	
	public String remover(){
		String page = null;
			genericoJPA.remover(despesaSelecionada);
			load();
			AlertaUtil.alerta("Removido com sucesso!");
			page = "list";
		
		return page;
	}
	
	private boolean valida(Despesa despesa){
	  boolean result = true;
	  if(!genericoJPA.buscarTodos("descricao", despesa.getDescricao(), Despesa.class).isEmpty()){
	    result = false;
	    AlertaUtil.alerta("Nome Inválido",AlertaUtil.ERROR);
	  }
	  return result;
	}
	
	private void limpar(){
	  despesaSelecionada = null;
	}
	
	private void getParameters(){
		String despesaId = util.getParameters("despesaId");
		if(despesaId != null && !despesaId.isEmpty()){
			despesaSelecionada = genericoJPA.findById(Despesa.class, Integer.parseInt(despesaId));
		}
		
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

	public List<Fornecedor> getListFornecedor() {
		return listFornecedor;
	}

	public void setListFornecedor(List<Fornecedor> listFornecedor) {
		this.listFornecedor = listFornecedor;
	}

	public int getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(int fornecedorId) {
		this.fornecedorId = fornecedorId;
	}
	
	

	
}
