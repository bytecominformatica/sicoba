package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Cliente;

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

	public DashboardController() {
	}

	@PostConstruct
	public void load() {
		listClientesInstalados = genericoJPA.buscarTodos(Cliente.class, false, "createdAt", 10);
	}

	public List<Cliente> getListClientesInstalados() {
		return listClientesInstalados;
	}

	public void setListClientesInstalados(List<Cliente> listClientesInstalados) {
		this.listClientesInstalados = listClientesInstalados;
	}

}
