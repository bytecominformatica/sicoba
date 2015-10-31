package net.servehttp.bytecom.service.financeiro;

import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.commons.parse.ParseRetornoCaixa;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by clairtonluz on 31/10/15.
 */
public class RetornoCaixaService implements Serializable {
    private static ParseRetornoCaixa PARSE_RETORNO = new ParseRetornoCaixa();

    public Header parse(InputStream inputStream, String filename) throws IOException {
        return PARSE_RETORNO.parse(inputStream, filename);
    }
}
