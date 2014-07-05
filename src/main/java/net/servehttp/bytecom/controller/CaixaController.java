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
import net.servehttp.bytecom.persistence.entity.caixa.Registro;
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
		Header header = caixaEJB.tratarArquivo(file);
		
		if(isValido(header)){
			genericoJPA.salvar(header);
		}
	}

	private boolean isValido(Header header) {
		boolean valido = true;
		
		List<Header> list = genericoJPA.buscarTodos("sequencial", header.getSequencial(), Header.class);
		if (!list.isEmpty()) {
			valido = false;
			AlertaUtil.alerta("Arquivo já foi enviado", AlertaUtil.WARN);
		}
		pagina 31 trailer do lote
		
		return valido;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
