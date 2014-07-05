package net.servehttp.bytecom.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Part;

import net.servehttp.bytecom.controller.CaixaController;
import net.servehttp.bytecom.persistence.entity.caixa.Header;
import net.servehttp.bytecom.persistence.entity.caixa.HeaderLote;
import net.servehttp.bytecom.persistence.entity.caixa.Registro;
import net.servehttp.bytecom.persistence.entity.caixa.RegistroDetalhe;
import net.servehttp.bytecom.persistence.entity.caixa.Trailer;
import net.servehttp.bytecom.util.StringUtil;

public class CaixaEJB {
	private final int HEADER = 0;
	private final int HEADER_LOTE = 1;
	private final int REGISTRO = 3;
	private final int DETALHE6 = 6;
	private final int TRAILER = 9;

	public Header tratarArquivo(Part file) {
		Header header = null;
		if (isArquivoRetorno(file)) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						file.getInputStream()));
				String line = null;
				List<Registro> listRegistros = new ArrayList<>();

				while ((line = in.readLine()) != null) {
					int tipoRegistro = StringUtil.INSTANCE.getInt(line, 7, 8);
					switch (tipoRegistro) {
					case HEADER:
						header = preencheHeader(line, file);
						break;
					case HEADER_LOTE:
						header.setHeaderLote(preencheHeaderLote(line, header));
						break;
					case REGISTRO:
						listRegistros.add(preencheRegistro(line, header, in));
						break;
					case TRAILER:
						header.setTrailer(preencheTrailer(line, header));
						break;
					}
				}

				header.getHeaderLote().setRegistros(listRegistros);
			} catch (IOException e) {
				Logger.getLogger(CaixaController.class.getName()).log(
						Level.SEVERE, null, e);
			}
		}

		return header;
	}

	private Header preencheHeader(String line, Part file) {
		Header h = new Header();
		h.setNomeArquivo(file.getSubmittedFileName());
		h.setDataGeracao(StringUtil.INSTANCE.getDataHora(line, 143, 157));
		h.setSequencial(StringUtil.INSTANCE.getInt(line, 157, 163));
		return h;
	}

	private HeaderLote preencheHeaderLote(String line, Header header) {
		HeaderLote hl = new HeaderLote();
		hl.setHeader(header);
		hl.setTipoOperacao(StringUtil.INSTANCE.getChar(line, 8));
		hl.setTipoServico(StringUtil.INSTANCE.getInt(line, 9, 11));
		hl.setNumeroRemessaRetorno(StringUtil.INSTANCE.getInt(line, 183, 191));
		hl.setDataGravacaoRemessaRetorno(StringUtil.INSTANCE.getData(line, 191,199));
		return hl;
	}

	private Registro preencheRegistro(String line, Header header, BufferedReader in) throws IOException {
		Registro r = new Registro();
		r.setHeaderLote(header.getHeaderLote());
		r.setNumeroRegistroNoLote(StringUtil.INSTANCE.getInt(line, 8, 13));
		r.setModalidadeNossoNumero(StringUtil.INSTANCE.getInt(line, 39, 41));
		r.setNossoNumero(StringUtil.INSTANCE.getInt(line, 41, 56));
		r.setCodigoCarteira(StringUtil.INSTANCE.getInt(line, 57, 58));
		r.setVencimento(StringUtil.INSTANCE.getData(line, 73, 81));
		r.setValorTitulo(StringUtil.INSTANCE.getDouble2Decimal(line, 81, 96));
		r.setValorTarifa(StringUtil.INSTANCE.getDouble2Decimal(line, 198, 212));
		
		if (((line = in.readLine()) != null)) {
			int tipoDetalhe = StringUtil.INSTANCE.getInt(line, 15, 17);
			
			RegistroDetalhe rd = new RegistroDetalhe();
			rd.setRegistro(r);
			rd.setNumeroRegistroNoLote(StringUtil.INSTANCE.getInt(line, 8, 13));
			rd.setJurosMultasEncargos(StringUtil.INSTANCE.getDouble2Decimal(line, 17, 32));
			rd.setDesconto(StringUtil.INSTANCE.getDouble2Decimal(line, 32, 47));
			rd.setAbatimento(StringUtil.INSTANCE.getDouble2Decimal(line, 47, 62));
			rd.setIof(StringUtil.INSTANCE.getDouble2Decimal(line, 62, 77));
			rd.setValorPago(StringUtil.INSTANCE.getDouble2Decimal(line, 77, 92));
			rd.setValorLiquido(StringUtil.INSTANCE.getDouble2Decimal(line, 92, 107));
			rd.setDataOcorrencia(StringUtil.INSTANCE.getData(line, 137, 145));
			rd.setDataCredito(StringUtil.INSTANCE.getData(line, 145, 153));
			
			if (tipoDetalhe == DETALHE6) {	
				rd.setDataDebitoTarifa(StringUtil.INSTANCE.getData(line, 157, 165));
			}
			r.setRegistroDetalhe(rd);
		}

		return r;
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
