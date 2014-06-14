package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Fornecedor;
import net.servehttp.bytecom.persistence.entity.Endereco;
import net.servehttp.bytecom.util.AlertaUtil;

@Named
@ViewScoped
public class FornecedorController implements Serializable {

  private static final long serialVersionUID = 5557798779948742363L;
  private List<Fornecedor> listFornecedor;
  private List<Endereco> listEndereco;
  private int enderecoId;
  private Fornecedor fornecedorSelecionado;
  private Fornecedor novoFornecedor = new Fornecedor();
  
  @Inject
  private Util util;
  @Inject
  private GenericoJPA genericoJPA;
  
  public FornecedorController(){}
  
  @PostConstruct
  public void load(){
    listFornecedor = genericoJPA.buscarTodos(Fornecedor.class);
    //setListEndereco(genericoJPA.buscarTodos(Endereco.class));
    limpar();
    getParameters();
  }
  
  public String salvar(){
    String page = null;
    if(valida(novoFornecedor)){
      novoFornecedor.setEndereco(genericoJPA.buscarPorId(Endereco.class, enderecoId));
      genericoJPA.salvar(novoFornecedor);
      AlertaUtil.alerta("Fornecedor gravado com sucesso!");
      load();
      novoFornecedor = new Fornecedor();
      page = "list";
    }return page;
  }
  
  public void atualizar(){
    genericoJPA.atualizar(fornecedorSelecionado);
    load();
  }
  
  public String remover(){
    String page = null;
    genericoJPA.remover(fornecedorSelecionado);
    load();
    AlertaUtil.alerta("Removido com sucesso!");
    page = "list";
    
    return page;
  }
  
  private void getParameters(){
    String fornecedorId = util.getParameters("fornecedorId");
    if(fornecedorId != null && !fornecedorId.isEmpty()){
      fornecedorSelecionado = genericoJPA.buscarPorId(Fornecedor.class, Integer.parseInt(fornecedorId));
    }
  }
  
  private boolean valida(Fornecedor fornecedor){
    boolean result = true;
    if(!genericoJPA.buscarTodos("nome",fornecedor.getNome(),Fornecedor.class).isEmpty()){
      result = false;
      AlertaUtil.alerta("Nome Inválido", AlertaUtil.ERROR);
    }return result;
  }
  
  public void limpar(){
    fornecedorSelecionado = null;
  }

  public List<Fornecedor> getListFornecedor() {
    return listFornecedor;
  }

  public void setListFornecedor(List<Fornecedor> listFornecedor) {
    this.listFornecedor = listFornecedor;
  }

  public List<Endereco> getListEndereco() {
    return listEndereco;
  }

  public void setListEndereco(List<Endereco> listEndereco) {
    this.listEndereco = listEndereco;
  }

  public int getEnderecoId() {
    return enderecoId;
  }

  public void setEnderecoId(int enderecoId) {
    this.enderecoId = enderecoId;
  }

  public Fornecedor getFornecedorSelecionado() {
    return fornecedorSelecionado;
  }

  public void setFornecedorSelecionado(Fornecedor fornecedorSelecionado) {
    this.fornecedorSelecionado = fornecedorSelecionado;
  }

  public Fornecedor getNovoFornecedor() {
    return novoFornecedor;
  }

  public void setNovoFornecedor(Fornecedor novoFornecedor) {
    this.novoFornecedor = novoFornecedor;
  }
  
  

}
