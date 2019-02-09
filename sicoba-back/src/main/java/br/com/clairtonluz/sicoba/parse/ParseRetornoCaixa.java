package br.com.clairtonluz.sicoba.parse;

import br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno.*;
import br.com.clairtonluz.sicoba.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ParseRetornoCaixa {

    private static final int HEADER = 0;
    private static final int HEADER_LOTE = 1;
    private static final int REGISTRO = 3;
    private static final int TRAILER_LOTE = 5;
    private static final int TRAILER = 9;

    public Header parse(InputStream inputStream, String filename) throws IOException {
        Header h = null;
        h = lerArquivoRetornoCaixa(inputStream, filename);
        return h;
    }


    private Header lerArquivoRetornoCaixa(InputStream input, String fileName) throws IOException {
        Header header = null;
        HeaderLote headerLote = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String line = null;

        while ((line = in.readLine()) != null) {
            int tipo = StringUtil.getInt(line, 7, 8);
            switch (tipo) {
                case HEADER:
                    header = preencheHeader(line, fileName);
                    break;
                case HEADER_LOTE:
                    headerLote = preencheHeaderLote(line, header);
                    break;
                case REGISTRO:
                    headerLote.getRegistros().add(preencheRegistro(line, headerLote, in));
                    break;
                case TRAILER_LOTE:
                    headerLote.setTrailerLote(preencheTrailerLote(line, headerLote));
                    header.getHeaderLotes().add(headerLote);
                    break;
                case TRAILER:
                    header.setTrailer(preencheTrailer(line, header));
                    break;
            }
        }

        if (isCorrompido(header)) {
            header = null;
            throw new IllegalArgumentException("O ARQUIVO DE RETORNO " + fileName + " ESTÁ CORROMPIDO!");
        }

        return header;
    }

    private boolean isCorrompido(Header header) {
        boolean corrompido = false;

        int quantidadeLotes = header.getTrailer().getQuantidadeLotes();
        if (quantidadeLotes != header.getHeaderLotes().size()) {
            corrompido = true;
        } else {

            int quantidadeRegistros = header.getTrailer().getQuantidadeRegistros();
            int quantidadeRegistrosReal = 2; // INICIA COM DOIS POIS O HEADER E O TRAILER TAMBÉM CONTA

            for (HeaderLote hl : header.getHeaderLotes()) {
                quantidadeRegistrosReal++; // +1 PARA CADA HEADER LOTE

                for (Registro r : hl.getRegistros()) {
                    quantidadeRegistrosReal++; // +1 PARA CADA REGISTRO
                    if (r.getRegistroDetalhe() != null) {
                        quantidadeRegistrosReal++; // +1 PARA CADA REGISTRO DETALHE
                    }
                }

                if (hl.getTrailerLote() != null) {
                    quantidadeRegistrosReal++; // +1 PARA CADA TRAILER LOTE
                }
            }

            if (quantidadeRegistros != quantidadeRegistrosReal) {
                corrompido = true;
            }
        }
        return corrompido;
    }

    private Header preencheHeader(String line, String fileName) {
        Header h = new Header();
        h.setNomeArquivo(fileName);
        h.setDataGeracao(StringUtil.getDataHora(line, 143, 157));
        h.setSequencial(StringUtil.getInt(line, 157, 163));

        h.setHeaderLotes(new ArrayList<HeaderLote>());
        return h;
    }

    private HeaderLote preencheHeaderLote(String line, Header header) {
        HeaderLote hl = new HeaderLote();
        hl.setHeader(header);
        hl.setNumeroRemessaRetorno(StringUtil.getInt(line, 183, 191));
        hl.setDataGravacaoRemessaRetorno(StringUtil.getData(line, 191, 199));

        hl.setRegistros(new ArrayList<Registro>());
        return hl;
    }

    private Registro preencheRegistro(String line, HeaderLote headerLote, BufferedReader in)
            throws IOException {

        Registro r = new Registro();
        r.setCodigoMovimento(StringUtil.getInt(line, 15, 17));
        if (r.getCodigoMovimento() != Registro.TARIFA) {
            r.setModalidadeNossoNumero(StringUtil.getInt(line, 39, 41));
            r.setNossoNumero(StringUtil.getInt(line, 41, 56));
            r.setNumeroDocumento(StringUtil.get(line, 58, 69).trim());
            r.setVencimento(StringUtil.getData(line, 73, 81));

        }
        r.setHeaderLote(headerLote);
        r.setValorTitulo(StringUtil.getDouble2Decimal(line, 81, 96));
        r.setBancoRecebedor(StringUtil.get(line, 96, 99));
        r.setAgenciaRecebedor(StringUtil.get(line, 99, 104));
        r.setDigitoVerificadorRecebedor(StringUtil.get(line, 104, 105));
        r.setUsoDaEmpresa(StringUtil.get(line, 105, 130));
        r.setValorTarifa(StringUtil.getDouble2Decimal(line, 198, 213));

        if ((line = in.readLine()) != null) {


            RegistroDetalhe rd = new RegistroDetalhe();
            rd.setRegistro(r);
            rd.setJurosMultasEncargos(StringUtil.getDouble2Decimal(line, 17, 32));
            rd.setDesconto(StringUtil.getDouble2Decimal(line, 32, 47));
            rd.setAbatimento(StringUtil.getDouble2Decimal(line, 47, 62));
            rd.setIof(StringUtil.getDouble2Decimal(line, 62, 77));
            rd.setValorPago(StringUtil.getDouble2Decimal(line, 77, 92));
            rd.setValorLiquido(StringUtil.getDouble2Decimal(line, 92, 107));
            rd.setDataOcorrencia(StringUtil.getData(line, 137, 145));
            String dataCredito = StringUtil.get(line, 145, 153);
            if (!dataCredito.equals("00000000")) {
                rd.setDataCredito(StringUtil.getData(line, 145, 153));
            }

            if (r.getCodigoMovimento() == Registro.LIQUIDACAO) {
                rd.setDataDebitoTarifa(StringUtil.getData(line, 157, 165));
            }
            r.setRegistroDetalhe(rd);

        }

        return r;
    }

    private TrailerLote preencheTrailerLote(String line, HeaderLote headerLote) {
        TrailerLote tl = new TrailerLote();
        tl.setHeaderLote(headerLote);
        tl.setQuantidadeRegistroLote(StringUtil.getInt(line, 17, 23));
        return tl;
    }

    private Trailer preencheTrailer(String line, Header header) {
        Trailer t = new Trailer();
        t.setHeader(header);
        t.setQuantidadeLotes(StringUtil.getInt(line, 17, 23));
        t.setQuantidadeRegistros(StringUtil.getInt(line, 23, 29));
        return t;
    }
}
