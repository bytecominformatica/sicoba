package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import net.servehttp.bytecom.ejb.CaixaEJB;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Mensalidade;
import net.servehttp.bytecom.persistence.entity.caixa.Header;
import net.servehttp.bytecom.persistence.entity.caixa.HeaderLote;
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
		if (file != null) {
			Header header = null;
			try {
				header = caixaEJB.tratarArquivo(file);
				if (notExists(header)) {
					for(HeaderLote hl : header.getHeaderLotes()){
						for(Registro r : hl.getRegistros()){
							Mensalidade m = genericoJPA.buscarUm("numeroBoleto", r.getNossoNumero(), Mensalidade.class);
							if(m != null){
								m.setStatus(Mensalidade.PAGA);
								m.setValor(r.getRegistroDetalhe().getValorPago());
								genericoJPA.atualizar(m);
							}
						}
					}
					genericoJPA.salvar(header);
					AlertaUtil.alerta("Arquivo enviado com sucesso!");
				}
			} catch (IllegalArgumentException e) {
				AlertaUtil.alerta("Arquivo corrompido!", AlertaUtil.ERROR);
			}
		} else {
			AlertaUtil.alerta("Nenhum arquivo selecionado!", AlertaUtil.WARN);
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
