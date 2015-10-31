package net.servehttp.bytecom.service;

import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.*;
import net.servehttp.bytecom.service.financeiro.RetornoCaixaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by clairtonluz on 31/10/15.
 */
public class RetornoCaixaServiceTest {

    private RetornoCaixaService retornoCaixaService = new RetornoCaixaService();
    private InputStream file;
    private String filename = "ret000149.ret";

    @Before
    public void setup(){

        file = getClass().getResourceAsStream("/files/" + filename);
        assertNotNull(file);
    }

    @After
    public void cleanup() throws IOException {
        file.close();
    }

    @Test
    public void deveriaVerificarOsDadosDoHeader() throws IOException {
        Header header = retornoCaixaService.parse(file, filename);

        assertNotNull(header);
        assertEquals(filename, header.getNomeArquivo());
        assertEquals(149, header.getSequencial());
        assertEquals(LocalDateTime.of(2015, 2, 19, 1, 0, 46), header.getDataGeracao());
        assertEquals(1, header.getHeaderLotes().size());

        HeaderLote headerLote = header.getHeaderLotes().get(0);
        assertNotNull(headerLote);

    }

    @Test
    public void deveriaVerificarOsDadosDoTrailer() throws IOException {
        Header header = retornoCaixaService.parse(file, filename);

        Trailer trailer = header.getTrailer();
        assertNotNull(trailer);
        assertEquals(1, trailer.getQuantidadeLotes());
        assertEquals(14, trailer.getQuantidadeRegistros());
    }

    @Test
    public void deveriaVerificarOsDadosDoHeaderLote() throws IOException {
        Header header = retornoCaixaService.parse(file, filename);

        HeaderLote headerLote = header.getHeaderLotes().get(0);
        assertNotNull(headerLote);
        assertEquals(149, headerLote.getNumeroRemessaRetorno());
        assertEquals(LocalDate.of(2015, 2, 19), headerLote.getDataGravacaoRemessaRetorno());
        assertEquals(5, headerLote.getRegistros().size());
    }

    @Test
    public void deveriaVerificarOsDadosDoTrailerLote() throws IOException {
        Header header = retornoCaixaService.parse(file, filename);

        TrailerLote trailerLote = header.getHeaderLotes().get(0).getTrailerLote();
        assertNotNull(trailerLote);
        assertEquals(12, trailerLote.getQuantidadeRegistroLote());
    }

    @Test
    public void deveriaVerificarOsDadosDoRegistro() throws IOException {
        Header header = retornoCaixaService.parse(file, filename);
        List<Registro> registros = header.getHeaderLotes().get(0).getRegistros();

        assertEquals(5, registros.size());

        Registro registro = registros.get(0);
        assertEquals(24, registro.getModalidadeNossoNumero());
        assertEquals(96, registro.getNossoNumero());
        assertNotNull(registro.getRegistroDetalhe());
        assertEquals(4.43d, registro.getValorTarifa(), 0d);
        assertEquals(35d, registro.getValorTitulo(), 0d);
        assertEquals(LocalDate.of(2015, 3, 20), registro.getVencimento());

    }
}
