package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Tecnico;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author felipe
 *
 */

@Named
@ViewScoped
public class TecnicoController implements Serializable {

  private static final long serialVersionUID = 1056135225775985204L;
  private List<Tecnico> listTecnico;
  private Tecnico tecnico = new Tecnico();
  private Tecnico tecnicoSelecionado;
  private String page;
  @Inject
  private GenericoJPA genericoJPA;
  @Inject
  private Util util;
  
  public TecnicoController(){}
  
  @PostConstruct
  public void load(){
    listTecnico = genericoJPA.buscarTodos(Tecnico.class);
    getParameters();
  }
  
  private void getParameters(){
    String tecnicoId = util.getParameters("id");
    if(tecnicoId != null && !tecnicoId.isEmpty()){
      tecnico = genericoJPA.findById(Tecnico.class, Integer.parseInt(tecnicoId));
    }
  }
  
  private boolean valida(Tecnico tecnico){
    boolean result = true;
    List<Tecnico> tecnicos = genericoJPA.buscarTodos("email", tecnico.getEmail(), Tecnico.class);
    if(!genericoJPA.buscarTodos("nome", tecnico.getNome(), Tecnico.class).isEmpty()){
      result = false;
      AlertaUtil.alerta("Nome Inválido", AlertaUtil.ERROR);
    }
    tecnicos = genericoJPA.buscarTodos("email", tecnico.getEmail(), Tecnico.class);
    if(!tecnicos.isEmpty() && tecnicos.get(0).getId() != tecnico.getId()){
      AlertaUtil.alerta("E-Mail já Cadastrado", AlertaUtil.ERROR);
      result = false;
    }
    return result;
  }
  
  public String salvar(){
    page = null;
    if(valida(tecnico)){
      if(tecnico.getId() == 0){
        genericoJPA.salvar(tecnico);
        AlertaUtil.alerta("Tecnico adicionado com sucesso!");
      }else{
        genericoJPA.atualizar(tecnico);
        AlertaUtil.alerta("Tecnico atualizado com sucesso!");
      }
      page = "list";
    }
    return page;
  }
  
  public String remover(){
    page = null;
    genericoJPA.remover(tecnico);
    page = "list";
    
    return page;
  }

  public List<Tecnico> getListTecnico() {
    return listTecnico;
  }

  public void setListTecnico(List<Tecnico> listTecnico) {
    this.listTecnico = listTecnico;
  }

  public Tecnico getTecnico() {
    return tecnico;
  }

  public void setTecnico(Tecnico tecnico) {
    this.tecnico = tecnico;
  }

  public Tecnico getTecnicoSelecionado() {
    return tecnicoSelecionado;
  }

  public void setTecnicoSelecionado(Tecnico tecnicoSelecionado) {
    this.tecnicoSelecionado = tecnicoSelecionado;
  }
  
  
  

}
