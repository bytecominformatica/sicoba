package net.servehttp.bytecom.service.financeiro;

import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.HeaderLote;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Registro;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.RegistroDetalhe;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by clairton on 09/10/15.
 */
public class ParseArquivoRetornoCaixaTest {

    private ParseArquivoRetornoCaixa parseArquivoRetornoCaixa = new ParseArquivoRetornoCaixa();
    private Header header;
    private static final double DELTA = 0d;

    @Before
    public void beforeEach() throws IOException {
        InputStream input = getClass().getResourceAsStream("/files/ret000149.ret");
        assertNotNull(input);
        header = parseArquivoRetornoCaixa.parse(input, "ret000149.ret");
    }

    @Test
    public void deveriaVerificarTratamentoDoHeader() throws IOException {
        assertEquals("ret000149.ret", header.getNomeArquivo());
        assertEquals(LocalDateTime.of(2015, 2, 19, 1, 0, 46), header.getDataGeracao());
        assertEquals(149, header.getSequencial());
        assertEquals(1, header.getHeaderLotes().size());
        assertEquals(1, header.getTrailer().getQuantidadeLotes());
        assertEquals(14, header.getTrailer().getQuantidadeRegistros());
    }

    @Test
    public void deveriaVerificarTratamentoDoTrailer() throws IOException {
        assertEquals(1, header.getTrailer().getQuantidadeLotes());
        assertEquals(14, header.getTrailer().getQuantidadeRegistros());
    }

    @Test
    public void deveriaVerificarTratamentoDoHeaderLote() throws IOException {
        HeaderLote headerLote = header.getHeaderLotes().get(0);
        assertEquals(149, headerLote.getNumeroRemessaRetorno());
        assertEquals(LocalDate.of(2015, 2, 19), headerLote.getDataGravacaoRemessaRetorno());
    }
    @Test
    public void deveriaVerificarTratamentoDoTrailerLote() throws IOException {
        HeaderLote headerLote = header.getHeaderLotes().get(0);
        assertEquals(12, headerLote.getTrailerLote().getQuantidadeRegistroLote());
    }

    @Test
    public void deveriaVerificarTratamentoDoRegistro() throws IOException {
        HeaderLote headerLote = header.getHeaderLotes().get(0);
        assertEquals(5, headerLote.getRegistros().size());
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
    @Test
    public void deveriaVerificarTratamentoDoRegistroDetalhe() throws IOException {
        HeaderLote headerLote = header.getHeaderLotes().get(0);
        RegistroDetalhe registroDetalhe = headerLote.getRegistros().get(0).getRegistroDetalhe();

        assertEquals(0d, registroDetalhe.getAbatimento(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataCredito());
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataDebitoTarifa());
        assertEquals(LocalDate.of(2015, 2, 19), registroDetalhe.getDataOcorrencia());
        assertEquals(0d, registroDetalhe.getDesconto(), DELTA);
        assertEquals(0d, registroDetalhe.getIof(), DELTA);
        assertEquals(0d, registroDetalhe.getJurosMultasEncargos(), DELTA);
        assertEquals(35d, registroDetalhe.getValorLiquido(), DELTA);
        assertEquals(35d, registroDetalhe.getValorPago(), DELTA);
    }

}
