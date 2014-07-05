package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.ejb.CaixaEJB;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.caixa.Header;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@Named
@RequestScoped
public class CaixaController implements Serializable {

	private static final long serialVersionUID = -3249445210310419657L;

	private Part file;

	@Inject
	private GenericoJPA genericoJPA;
	@Inject
	private CaixaEJB caixaEJB;

	public CaixaController() {
	}

	public void upload() {
		try {
			Header header = caixaEJB.tratarArquivo(file);
			if (notExists(header)) {
				genericoJPA.salvar(header);
				AlertaUtil.alerta("Arquivo enviado com sucesso!");
			}
		} catch (IllegalArgumentException e) {
			AlertaUtil.alerta("Arquivo corrompido!", AlertaUtil.ERROR);
		}
	}

	private boolean notExists(Header header) {
		boolean exists = false;

		List<Header> list = genericoJPA.buscarTodos("sequencial",
				header.getSequencial(), Header.class);
		if (!list.isEmpty()) {
			exists = true;
			AlertaUtil.alerta("Arquivo já foi enviado", AlertaUtil.WARN);
		}

		return !exists;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
