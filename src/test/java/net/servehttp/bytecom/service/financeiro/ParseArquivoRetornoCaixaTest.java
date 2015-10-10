package net.servehttp.bytecom.service.financeiro;

import static org.junit.Assert.*;

import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.HeaderLote;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Registro;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by clairton on 09/10/15.
 */
public class ParseArquivoRetornoCaixaTest {

    private ParseArquivoRetornoCaixa parseArquivoRetornoCaixa = new ParseArquivoRetornoCaixa();
    private static final double DELTA = 0d;

    @Test
    public void deveriaTratarOArquivoDeRetornoCaixa() throws IOException {
        InputStream input = getClass().getResourceAsStream("/files/ret000149.ret");
        assertNotNull(input);

        Header h = parseArquivoRetornoCaixa.parse(input, "ret000149.ret");

        assertNotNull(h);
        assertEquals("ret000149.ret", h.getNomeArquivo());
        assertEquals(LocalDateTime.of(2015, 2, 19, 1, 0, 46), h.getDataGeracao());
        assertEquals(149, h.getSequencial());
        assertEquals(1, h.getHeaderLotes().size());
        assertEquals(1, h.getTrailer().getQuantidadeLotes());
        assertEquals(14, h.getTrailer().getQuantidadeRegistros());

        HeaderLote headerLote = h.getHeaderLotes().get(0);
        assertEquals(149, headerLote.getNumeroRemessaRetorno());
        assertEquals(LocalDate.of(2015, 2, 19), headerLote.getDataGravacaoRemessaRetorno());
        assertEquals(5, headerLote.getRegistros().size());
        assertEquals(12, headerLote.getTrailerLote().getQuantidadeRegistroLote());

        Registro registro = headerLote.getRegistros().get(0);

        assertEquals(24, registro.getModalidadeNossoNumero());
        assertEquals(96, registro.getNossoNumero());
        assertEquals(0.44d, registro.getValorTarifa(), DELTA);
        assertEquals(35d, registro.getValorTitulo(), DELTA);
        assertEquals(LocalDate.of(2015, 3, 20), registro.getVencimento());

        assertEquals(0d, registro.getRegistroDetalhe().getAbatimento(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registro.getRegistroDetalhe().getDataCredito());
        assertEquals(LocalDate.of(2015, 2, 20), registro.getRegistroDetalhe().getDataDebitoTarifa());
        assertEquals(LocalDate.of(2015, 2, 19), registro.getRegistroDetalhe().getDataOcorrencia());
        assertEquals(0d, registro.getRegistroDetalhe().getDesconto(), DELTA);
        assertEquals(0d, registro.getRegistroDetalhe().getIof(), DELTA);
        assertEquals(0d, registro.getRegistroDetalhe().getJurosMultasEncargos(), DELTA);
        assertEquals(35d, registro.getRegistroDetalhe().getValorLiquido(), DELTA);
        assertEquals(35d, registro.getRegistroDetalhe().getValorPago(), DELTA);
    }

}
