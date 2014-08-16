package net.servehttp.bytecom.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Part;

import net.servehttp.bytecom.controller.CaixaController;
import net.servehttp.bytecom.persistence.entity.caixa.Header;
import net.servehttp.bytecom.persistence.entity.caixa.HeaderLote;
import net.servehttp.bytecom.persistence.entity.caixa.Registro;
import net.servehttp.bytecom.persistence.entity.caixa.RegistroDetalhe;
import net.servehttp.bytecom.persistence.entity.caixa.Trailer;
import net.servehttp.bytecom.persistence.entity.caixa.TrailerLote;
import net.servehttp.bytecom.util.StringUtil;

public class CaixaEJB {
	private static final int HEADER = 0;
	private static final int HEADER_LOTE = 1;
	private static final int REGISTRO = 3;
	private static final int DETALHE6 = 6;
	private static final int TRAILER_LOTE = 5;
	private static final int TRAILER = 9;

	public Header tratarArquivo(Part file) {
		Header header = null;
		HeaderLote headerLote = null;
		if (isArquivoRetorno(file)) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						file.getInputStream()));
				String line = null;

				while ((line = in.readLine()) != null) {
					int tipoRegistro = StringUtil.INSTANCE.getInt(line, 7, 8);
					switch (tipoRegistro) {
					case HEADER:
						header = preencheHeader(line, file);
						break;
					case HEADER_LOTE:
						headerLote = preencheHeaderLote(line, header);
						break;
					case REGISTRO:
						headerLote.getRegistros().add(
								preencheRegistro(line, headerLote, in));
						break;
					case TRAILER_LOTE:
						headerLote.setTrailerLote(preencheTrailerLote(line,
								headerLote));
						header.getHeaderLotes().add(headerLote);
						break;
					case TRAILER:
						header.setTrailer(preencheTrailer(line, header));
						break;
					}
				}

			} catch (IOException e) {
				Logger.getLogger(CaixaController.class.getName()).log(
						Level.SEVERE, null, e);
			}
		}
		
		if(isCorrompido(header)){
			header = null;
			throw new IllegalArgumentException("O ARQUIVO DE RETORNO " + file.getSubmittedFileName() + " ESTÁ CORROMPIDO!");
		}

		return header;
	}

	private boolean isCorrompido(Header header) {
		boolean corrompido = false;

		int quantidadeLotes = header.getTrailer().getQuantidadeLotes();
		if (quantidadeLotes != header.getHeaderLotes().size()) {
			corrompido = true;
		} else {

			int quantidadeRegistros = header.getTrailer()
					.getQuantidadeRegistros();
			int quantidadeRegistrosReal = 2; 													// INICIA COM DOIS POIS O HEADER E O TRAILER TAMBÉM CONTA

			for (HeaderLote hl : header.getHeaderLotes()) {
				quantidadeRegistrosReal++; 														// +1 PARA CADA HEADER LOTE

				for (Registro r : hl.getRegistros()) {
					quantidadeRegistrosReal++;													// +1 PARA CADA REGISTRO
					if (r.getRegistroDetalhe() != null) {
						quantidadeRegistrosReal++; 												// +1 PARA CADA REGISTRO DETALHE
					}
				}

				if (hl.getTrailerLote() != null) {
					quantidadeRegistrosReal++; 													// +1 PARA CADA TRAILER LOTE
				}
			}

			if(quantidadeRegistros != quantidadeRegistrosReal){
				corrompido = true;
			}
		}
		return corrompido;
	}

	private Header preencheHeader(String line, Part file) {
		Header h = new Header();
		h.setNomeArquivo(file.getSubmittedFileName());
		h.setDataGeracao(StringUtil.INSTANCE.getDataHora(line, 143, 157));
		h.setSequencial(StringUtil.INSTANCE.getInt(line, 157, 163));

		h.setHeaderLotes(new ArrayList<HeaderLote>());
		return h;
	}

	private HeaderLote preencheHeaderLote(String line, Header header) {
		HeaderLote hl = new HeaderLote();
		hl.setHeader(header);
		hl.setNumeroRemessaRetorno(StringUtil.INSTANCE.getInt(line, 183, 191));
		hl.setDataGravacaoRemessaRetorno(StringUtil.INSTANCE.getData(line, 191,
				199));

		hl.setRegistros(new ArrayList<Registro>());
		return hl;
	}

	private Registro preencheRegistro(String line, HeaderLote headerLote,
			BufferedReader in) throws IOException {
		Registro r = new Registro();
		r.setHeaderLote(headerLote);
		r.setModalidadeNossoNumero(StringUtil.INSTANCE.getInt(line, 39, 41));
		r.setNossoNumero(StringUtil.INSTANCE.getInt(line, 41, 56));
		r.setVencimento(StringUtil.INSTANCE.getData(line, 73, 81));
		r.setValorTitulo(StringUtil.INSTANCE.getDouble2Decimal(line, 81, 96));
		r.setValorTarifa(StringUtil.INSTANCE.getDouble2Decimal(line, 198, 212));

		if ((line = in.readLine()) != null) {
			int tipoDetalhe = StringUtil.INSTANCE.getInt(line, 15, 17);

			RegistroDetalhe rd = new RegistroDetalhe();
			rd.setRegistro(r);
			rd.setJurosMultasEncargos(StringUtil.INSTANCE.getDouble2Decimal(
					line, 17, 32));
			rd.setDesconto(StringUtil.INSTANCE.getDouble2Decimal(line, 32, 47));
			rd.setAbatimento(StringUtil.INSTANCE
					.getDouble2Decimal(line, 47, 62));
			rd.setIof(StringUtil.INSTANCE.getDouble2Decimal(line, 62, 77));
			rd.setValorPago(StringUtil.INSTANCE.getDouble2Decimal(line, 77, 92));
			rd.setValorLiquido(StringUtil.INSTANCE.getDouble2Decimal(line, 92,
					107));
			rd.setDataOcorrencia(StringUtil.INSTANCE.getData(line, 137, 145));
			rd.setDataCredito(StringUtil.INSTANCE.getData(line, 145, 153));

			if (tipoDetalhe == DETALHE6) {
				rd.setDataDebitoTarifa(StringUtil.INSTANCE.getData(line, 157,
						165));
			}
			r.setRegistroDetalhe(rd);
		}

		return r;
	}

	private TrailerLote preencheTrailerLote(String line, HeaderLote headerLote) {
		TrailerLote tl = new TrailerLote();
		tl.setHeaderLote(headerLote);
		tl.setQuantidadeRegistroLote(StringUtil.INSTANCE.getInt(line, 17, 23));
		return tl;
	}

	private Trailer preencheTrailer(String line, Header header) {
		Trailer t = new Trailer();
		t.setHeader(header);
		t.setQuantidadeLotes(StringUtil.INSTANCE.getInt(line, 17, 23));
		t.setQuantidadeRegistros(StringUtil.INSTANCE.getInt(line, 23, 29));
		return t;
	}

	private boolean isArquivoRetorno(Part file) {
		if (file.getSubmittedFileName().indexOf(".ret") == -1
				|| !"application/octet-stream".equals(file.getContentType())) {
			return false;
		}
		return true;
	}

}
