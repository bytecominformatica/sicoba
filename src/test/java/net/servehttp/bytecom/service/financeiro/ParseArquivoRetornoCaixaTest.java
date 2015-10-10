package net.servehttp.bytecom.service.financeiro;

import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.HeaderLote;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Registro;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.RegistroDetalhe;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a> on 09/10/15.
 */
public class ParseArquivoRetornoCaixaTest {

    private static Header HEADER;
    private static final double DELTA = 0d;

    @BeforeClass
    public static void before() throws IOException {
        InputStream input = ParseArquivoRetornoCaixaTest.class.getResourceAsStream("/files/ret000149.ret");
        assertNotNull(input);
        HEADER = new ParseArquivoRetornoCaixa().parse(input, "ret000149.ret");
    }

    @Test
    public void deveriaVerificarTratamentoDoHeader() throws IOException {
        assertEquals("ret000149.ret", HEADER.getNomeArquivo());
        assertEquals(LocalDateTime.of(2015, 2, 19, 1, 0, 46), HEADER.getDataGeracao());
        assertEquals(149, HEADER.getSequencial());
        assertEquals(1, HEADER.getHeaderLotes().size());
        assertEquals(1, HEADER.getTrailer().getQuantidadeLotes());
        assertEquals(14, HEADER.getTrailer().getQuantidadeRegistros());
    }

    @Test
    public void deveriaVerificarTratamentoDoTrailer() throws IOException {
        assertEquals(1, HEADER.getTrailer().getQuantidadeLotes());
        assertEquals(14, HEADER.getTrailer().getQuantidadeRegistros());
    }

    @Test
    public void deveriaVerificarTratamentoDoHeaderLote() throws IOException {
        HeaderLote headerLote = HEADER.getHeaderLotes().get(0);
        assertEquals(149, headerLote.getNumeroRemessaRetorno());
        assertEquals(LocalDate.of(2015, 2, 19), headerLote.getDataGravacaoRemessaRetorno());
    }
    @Test
    public void deveriaVerificarTratamentoDoTrailerLote() throws IOException {
        HeaderLote headerLote = HEADER.getHeaderLotes().get(0);
        assertEquals(12, headerLote.getTrailerLote().getQuantidadeRegistroLote());
    }

    @Test
    public void deveriaVerificarTratamentoDoRegistro() throws IOException {
        HeaderLote headerLote = HEADER.getHeaderLotes().get(0);
        assertEquals(5, headerLote.getRegistros().size());

        Registro registro = headerLote.getRegistros().get(0);
        assertEquals(24, registro.getModalidadeNossoNumero());
        assertEquals(96, registro.getNossoNumero());
        assertEquals(0.44d, registro.getValorTarifa(), DELTA);
        assertEquals(35d, registro.getValorTitulo(), DELTA);
        assertEquals(LocalDate.of(2015, 3, 20), registro.getVencimento());

        registro = headerLote.getRegistros().get(1);
        assertEquals(24, registro.getModalidadeNossoNumero());
        assertEquals(909, registro.getNossoNumero());
        assertEquals(0.44d, registro.getValorTarifa(), DELTA);
        assertEquals(55d, registro.getValorTitulo(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registro.getVencimento());

        registro = headerLote.getRegistros().get(2);
        assertEquals(24, registro.getModalidadeNossoNumero());
        assertEquals(788, registro.getNossoNumero());
        assertEquals(0.23d, registro.getValorTarifa(), DELTA);
        assertEquals(85d, registro.getValorTitulo(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registro.getVencimento());

        registro = headerLote.getRegistros().get(3);
        assertEquals(24, registro.getModalidadeNossoNumero());
        assertEquals(107, registro.getNossoNumero());
        assertEquals(0.44d, registro.getValorTarifa(), DELTA);
        assertEquals(59.9d, registro.getValorTitulo(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registro.getVencimento());

        registro = headerLote.getRegistros().get(4);
        assertEquals(24, registro.getModalidadeNossoNumero());
        assertEquals(764, registro.getNossoNumero());
        assertEquals(0.23d, registro.getValorTarifa(), DELTA);
        assertEquals(70d, registro.getValorTitulo(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registro.getVencimento());
    }

    @Test
    public void deveriaVerificarTratamentoDoRegistroDetalhe() throws IOException {
        List<Registro> registros = HEADER.getHeaderLotes().get(0).getRegistros();

        RegistroDetalhe registroDetalhe = registros.get(0).getRegistroDetalhe();
        assertEquals(0d, registroDetalhe.getAbatimento(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataCredito());
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataDebitoTarifa());
        assertEquals(LocalDate.of(2015, 2, 19), registroDetalhe.getDataOcorrencia());
        assertEquals(0d, registroDetalhe.getDesconto(), DELTA);
        assertEquals(0d, registroDetalhe.getIof(), DELTA);
        assertEquals(0d, registroDetalhe.getJurosMultasEncargos(), DELTA);
        assertEquals(35d, registroDetalhe.getValorLiquido(), DELTA);
        assertEquals(35d, registroDetalhe.getValorPago(), DELTA);

        registroDetalhe = registros.get(1).getRegistroDetalhe();
        assertEquals(0d, registroDetalhe.getAbatimento(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataCredito());
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataDebitoTarifa());
        assertEquals(LocalDate.of(2015, 2, 19), registroDetalhe.getDataOcorrencia());
        assertEquals(0d, registroDetalhe.getDesconto(), DELTA);
        assertEquals(0d, registroDetalhe.getIof(), DELTA);
        assertEquals(0d, registroDetalhe.getJurosMultasEncargos(), DELTA);
        assertEquals(55d, registroDetalhe.getValorLiquido(), DELTA);
        assertEquals(55d, registroDetalhe.getValorPago(), DELTA);

        registroDetalhe = registros.get(2).getRegistroDetalhe();
        assertEquals(0d, registroDetalhe.getAbatimento(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataCredito());
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataDebitoTarifa());
        assertEquals(LocalDate.of(2015, 2, 19), registroDetalhe.getDataOcorrencia());
        assertEquals(0d, registroDetalhe.getDesconto(), DELTA);
        assertEquals(0d, registroDetalhe.getIof(), DELTA);
        assertEquals(0d, registroDetalhe.getJurosMultasEncargos(), DELTA);
        assertEquals(85d, registroDetalhe.getValorLiquido(), DELTA);
        assertEquals(85d, registroDetalhe.getValorPago(), DELTA);

        registroDetalhe = registros.get(3).getRegistroDetalhe();
        assertEquals(0d, registroDetalhe.getAbatimento(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataCredito());
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataDebitoTarifa());
        assertEquals(LocalDate.of(2015, 2, 19), registroDetalhe.getDataOcorrencia());
        assertEquals(0d, registroDetalhe.getDesconto(), DELTA);
        assertEquals(0d, registroDetalhe.getIof(), DELTA);
        assertEquals(0d, registroDetalhe.getJurosMultasEncargos(), DELTA);
        assertEquals(59.9d, registroDetalhe.getValorLiquido(), DELTA);
        assertEquals(59.9d, registroDetalhe.getValorPago(), DELTA);

        registroDetalhe = registros.get(4).getRegistroDetalhe();
        assertEquals(0d, registroDetalhe.getAbatimento(), DELTA);
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataCredito());
        assertEquals(LocalDate.of(2015, 2, 20), registroDetalhe.getDataDebitoTarifa());
        assertEquals(LocalDate.of(2015, 2, 19), registroDetalhe.getDataOcorrencia());
        assertEquals(0d, registroDetalhe.getDesconto(), DELTA);
        assertEquals(0d, registroDetalhe.getIof(), DELTA);
        assertEquals(0d, registroDetalhe.getJurosMultasEncargos(), DELTA);
        assertEquals(70d, registroDetalhe.getValorLiquido(), DELTA);
        assertEquals(70d, registroDetalhe.getValorPago(), DELTA);
    }

}
