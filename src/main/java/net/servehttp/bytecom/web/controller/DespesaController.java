package net.servehttp.bytecom.web.controller;

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
import net.servehttp.bytecom.util.Util;

@Named
@ViewScoped
public class DespesaController implements Serializable {

  private static final long serialVersionUID = -5303584227915363975L;
  private List<Despesa> listDespesa;
  private List<Fornecedor> listFornecedor;
  @Inject
  private Despesa despesa;
  @Inject
  private Util util;
  @Inject
  private GenericoJPA genericoJPA;

  @PostConstruct
  public void load() {
    listDespesa = genericoJPA.buscarTodos(Despesa.class);
    listFornecedor = genericoJPA.buscarTodos(Fornecedor.class);
    limpar();
    getParameters();
  }

  public String salvar() {
    String page = null;
    if (valida(despesa)) {
      genericoJPA.salvar(despesa);
      AlertaUtil.info("Despesa gravada com sucesso!");
      load();
      despesa = new Despesa();
      page = "list";
    }
    return page;
  }

  public void atualizar() {
    genericoJPA.atualizar(despesa);
    load();
  }

  public String remover() {
    genericoJPA.remover(despesa);
    load();
    AlertaUtil.info("Removido com sucesso!");
    return "list";
  }

  private boolean valida(Despesa despesa) {
    boolean result = true;
    if (!genericoJPA.buscarTodos("descricao", despesa.getDescricao(), Despesa.class).isEmpty()) {
      result = false;
      AlertaUtil.error("Nome Inválido");
    }
    return result;
  }

  private void limpar() {
    setDespesa(new Despesa());
  }

  private void getParameters() {
    String despesaId = util.getParameters("despesaId");
    if (despesaId != null && !despesaId.isEmpty()) {
      setDespesa(genericoJPA.findById(Despesa.class, Integer.parseInt(despesaId)));
    }
  }

  public List<Despesa> getListDespesa() {
    return listDespesa;
  }

  public void setListDespesa(List<Despesa> listDespesa) {
    this.listDespesa = listDespesa;
  }

  public List<Fornecedor> getListFornecedor() {
    return listFornecedor;
  }

  public void setListFornecedor(List<Fornecedor> listFornecedor) {
    this.listFornecedor = listFornecedor;
  }

  public Despesa getDespesa() {
    return despesa;
  }

  public void setDespesa(Despesa despesa) {
    this.despesa = despesa;
  }

}
