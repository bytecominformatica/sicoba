package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Bairro;
import net.servehttp.bytecom.persistence.entity.Cidade;
import net.servehttp.bytecom.persistence.entity.Fornecedor;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author felipe
 *
 */
@Named
@ViewScoped
public class FornecedorController implements Serializable {

  private static final long serialVersionUID = 5557798779948742363L;
  private List<Fornecedor> listFornecedor;
  private List<Cidade> listCidades;
  private List<Bairro> listBairros;
  private int cidadeId;
  private int bairroId;
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
    setListCidades(genericoJPA.buscarTodos(Cidade.class));
    //limpar();
    getParameters();
  }
  
  public String salvar(){
    String page = null;
    if(valida(novoFornecedor)){
      novoFornecedor.getEndereco().setBairro(genericoJPA.buscarPorId(Bairro.class, bairroId));
      if(novoFornecedor.getId() == 0){
        genericoJPA.salvar(novoFornecedor);
        AlertaUtil.alerta("Fornecedor gravado com sucesso!");
      }else{
        genericoJPA.atualizar(novoFornecedor);
        AlertaUtil.alerta("Fornecedor atualizado com sucesso!");
      }
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
    String fornecedorId = util.getParameters("id");
    if(fornecedorId != null && !fornecedorId.isEmpty()){
      fornecedorSelecionado = genericoJPA.buscarPorId(Fornecedor.class, Integer.parseInt(fornecedorId));
      cidadeId = novoFornecedor.getEndereco().getBairro().getCidade().getId();
      bairroId = novoFornecedor.getEndereco().getBairro().getId();
    }
  }
  
  public void atualizaBairros() {
    for (Cidade c : listCidades) {
      if (c.getId() == cidadeId) {
        listBairros = c.getBairros();
      }
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

  public List<Cidade> getListCidades() {
    return listCidades;
  }

  public void setListCidades(List<Cidade> listCidade) {
    this.listCidades = listCidade;
  }

  public List<Bairro> getListBairros() {
    return listBairros;
  }

  public void setListBairros(List<Bairro> listBairro) {
    this.listBairros = listBairro;
  }

  public int getCidadeId() {
    return cidadeId;
  }

  public void setCidadeId(int cidadeId) {
    this.cidadeId = cidadeId;
  }

  public int getBairroId() {
    return bairroId;
  }

  public void setBairroId(int bairroId) {
    this.bairroId = bairroId;
  }
  
  
  

}
