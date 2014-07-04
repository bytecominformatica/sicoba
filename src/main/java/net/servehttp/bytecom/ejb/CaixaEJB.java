package net.servehttp.bytecom.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Part;

import net.servehttp.bytecom.controller.CaixaController;
import net.servehttp.bytecom.persistence.entity.caixa.Header;
import net.servehttp.bytecom.persistence.entity.caixa.Trailer;
import net.servehttp.bytecom.util.StringUtil;

public class CaixaEJB {

	public Header tratarArquivo(Part file) {
		Header header = null;
		if (isArquivoRetorno(file)) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						file.getInputStream()));
				String line = null;
				int i = 0;
				while ((line = in.readLine()) != null) {
					switch (StringUtil.INSTANCE.getInt(line, 7, 8)) {
					case 0:
						header = preencheHeader(line, file);
						break;
					case 1:
						if (header != null) {
							header.setTrailer(preencheTrailer(line, header));
						}
						break;
					case 9:
						if (header != null) {
							header.setTrailer(preencheTrailer(line, header));
						}
						break;
					}
				}
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
		h.setDataGeracao(StringUtil.INSTANCE.getData(line, 143, 157));
		h.setSequencial(StringUtil.INSTANCE.getInt(line, 157, 163));
		return h;
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

	public static void main(String... strings) {
		String line = "10400000         1000052749623510000000000000000000001089848492400000000CLAUDIO CARNEIRO LUZ          C ECON FEDERAL                          20206201402162000001304000000                    RETORNO-PRODUCAO                  000            ";
		CaixaEJB caixa = new CaixaEJB();
		// caixa.preencheHeader(line);
	}

}
