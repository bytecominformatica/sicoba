package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.ClienteJPA;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Bairro;
import net.servehttp.bytecom.persistence.entity.Cidade;
import net.servehttp.bytecom.persistence.entity.Cliente;
import net.servehttp.bytecom.pojo.EnderecoPojo;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.DateUtil;
import net.servehttp.bytecom.util.EnderecoUtil;
import net.servehttp.bytecom.util.StringUtil;

/**
 * 
 * @author clairton
 */
@Named
@RequestScoped
public class DashboardController implements Serializable {


  private static final long serialVersionUID = 8827281306259995250L;
 
  @Inject
  private GenericoJPA genericoJPA;
  
  private List<Cliente> listClientesInstalados;
  

  public DashboardController() {}

  @PostConstruct
  public void load() {
    setListClientesInstalados(genericoJPA.buscarJpql("select c from Cliente c where c.createAt > ?1", DateUtil.getPrimeiroDiaDoMes(), Cliente.class));
  }

public List<Cliente> getListClientesInstalados() {
	return listClientesInstalados;
}

public void setListClientesInstalados(List<Cliente> listClientesInstalados) {
	this.listClientesInstalados = listClientesInstalados;
}


}
