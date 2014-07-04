package net.servehttp.bytecom.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.ejb.CaixaEJB;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.caixa.Header;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.StringUtil;

/**
 * 
 * @author clairton
 */
@Named
@RequestScoped
public class CaixaController implements Serializable {

	private static final long serialVersionUID = -3249445210310419657L;

	private Part file;
	private String fileContent;

	@Inject
	private GenericoJPA genericoJPA;
	@Inject
	private CaixaEJB caixaEJB;

	public CaixaController() {
	}

	public void upload() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					file.getInputStream()));
			String line = null;
			int i = 0;
			while ((line = in.readLine()) != null) {
				switch ("") {

				}
			}
		} catch (IOException e) {
			Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE,
					null, e);
		}
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
