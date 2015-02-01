package net.servehttp.bytecom.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.servehttp.bytecom.commons.StringUtil;

public abstract class GerarBoleto implements Serializable {

  private static final double TAXA_JUROS_AO_DIA = 0.006677;
  private static final double TAXA_MULTA = 0.05;
  private static final int EMITENTE_BENEFICIARIO = 4;
  private static final long serialVersionUID = 2996334986455478857L;
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static byte[] criarCarneCaixa(List<Mensalidade> mensalidades,
      net.servehttp.bytecom.persistence.entity.financeiro.boleto.Cedente c) {
    if (mensalidades != null && !mensalidades.isEmpty()) {
      List<Boleto> boletos = new ArrayList<>();

      mensalidades.forEach(m -> {
        Cedente cedente = getCedente(c);

        Sacado sacado = getSacado(m.getCliente());

        Titulo titulo = getTitulo(m, cedente, sacado, c);

        Boleto boleto = getBoleto(m, titulo);

        boletos.add(boleto);
      });

      File templatePersonalizado = getTemplate();

      byte[] boletosPorPagina = groupInPages(boletos, templatePersonalizado);

      return boletosPorPagina;
    }
    return null;
  }

  private static File getTemplate() {
    File templatePersonalizado = new File("template.pdf");
    if (!templatePersonalizado.exists()) {
      try (InputStream resource =
          GerarBoleto.class.getClassLoader().getResourceAsStream(
              "/template/BoletoCarne3PorPagina.pdf")) {

        try (OutputStream outputStream = new FileOutputStream(templatePersonalizado)) {

          // write the inputStream to a FileOutputStream
          int read = 0;
          byte[] bytes = new byte[1024];

          while ((read = resource.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return templatePersonalizado;
  }

  private static Boleto getBoleto(Mensalidade m, Titulo titulo) {
    /*
     * INFORMANDO OS DADOS SOBRE O BOLETO.
     */
    Boleto boleto = new Boleto(titulo);

    boleto.setLocalPagamento("PREFERENCIALMENTE NAS CASAS LOTÉRICAS ATÉ O VALOR LIMITE");
    boleto.setInstrucao1(String.format("MULTA DE R$: %s APÓS : %s",
        StringUtil.formatCurrence(m.getValor() * TAXA_MULTA),
        m.getDataVencimento().format(formatter)));
    boleto.setInstrucao2(String.format("JUROS DE R$: %s AO DIA",
        StringUtil.formatCurrence(m.getValor() * TAXA_JUROS_AO_DIA)));
    boleto.setInstrucao4("NÃO RECEBER APOS 30 DIAS DO VENCIMENTO");
    if (m.getParcela() != null && !m.getParcela().isEmpty()) {
      boleto.setInstrucao6("PARCELA " + m.getParcela());
    }
    String nossoNumeroPadraoCEF =
        String.format("%d%d/%s-%s", titulo.getContaBancaria().getCarteira().getCodigo(),
            EMITENTE_BENEFICIARIO, titulo.getNossoNumero(), titulo.getDigitoDoNossoNumero());
    boleto.addTextosExtras("txtFcNossoNumero", nossoNumeroPadraoCEF);
    boleto.addTextosExtras("txtRsNossoNumero", nossoNumeroPadraoCEF);
    return boleto;
  }

  private static Titulo getTitulo(Mensalidade m, Cedente cedente, Sacado sacado,
      net.servehttp.bytecom.persistence.entity.financeiro.boleto.Cedente c) {
    /*
     * INFORMANDO OS DADOS SOBRE O TÍTULO.
     */

    // Informando dados sobre a conta bancária do título.
    ContaBancaria contaBancaria =
        new ContaBancaria(BancosSuportados.CAIXA_ECONOMICA_FEDERAL.create());
    contaBancaria.setNumeroDaConta(new NumeroDaConta(c.getCodigo(), String.valueOf(c
        .getDigitoVerificador())));
    contaBancaria.setCarteira(new Carteira(2, TipoDeCobranca.SEM_REGISTRO));
    contaBancaria
        .setAgencia(new Agencia(c.getNumeroAgencia(), String.valueOf(c.getDigitoAgencia())));

    ParametrosBancariosMap map = new ParametrosBancariosMap();
    map.adicione("CodigoOperacao", c.getCodigoOperacao());

    Titulo titulo = new Titulo(contaBancaria, sacado, cedente, map);

    titulo.setNumeroDoDocumento(String.format("%d/%d", m.getCliente().getId(), m.getId()));

    titulo.setNossoNumero(String.format("%015d", m.getNumeroBoleto() != null ? m.getNumeroBoleto()
        : m.getId()));
    titulo.setDigitoDoNossoNumero(calcularDigitoDoNossoNumero(contaBancaria.getCarteira()
        .getCodigo(), titulo.getNossoNumero()));
    titulo.setValor(BigDecimal.valueOf(m.getValor()));
    titulo.setDataDoDocumento(new Date());
    titulo.setDataDoVencimento(Date.from(m.getDataVencimento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
    titulo.setAceite(Aceite.N);
    return titulo;
  }

  private static Sacado getSacado(Cliente cliente) {
    /*
     * INFORMANDO DADOS SOBRE O SACADO.
     */
    Sacado sacado = new Sacado(cliente.getNome().toUpperCase(), cliente.getCpfCnpj());

    // Informando o endereço do sacado.
    Endereco enderecoSac = new Endereco();

    String uf = cliente.getEndereco().getBairro().getCidade().getEstado().getUf();
    enderecoSac.setUF(UnidadeFederativa.valueOf(uf));

    enderecoSac
        .setLocalidade(cliente.getEndereco().getBairro().getCidade().getNome().toUpperCase());
    enderecoSac.setCep(new CEP(cliente.getEndereco().getCep()));
    enderecoSac.setBairro(cliente.getEndereco().getBairro().getNome().toUpperCase());
    enderecoSac.setLogradouro(cliente.getEndereco().getLogradouro().toUpperCase());
    enderecoSac.setNumero(cliente.getEndereco().getNumero());
    sacado.addEndereco(enderecoSac);
    return sacado;
  }

  private static Cedente getCedente(net.servehttp.bytecom.persistence.entity.financeiro.boleto.Cedente c) {
    /*
     * INFORMANDO DADOS SOBRE O CEDENTE.
     */
    Cedente cedente = new Cedente(c.getNome(), c.getCpfCnpj());
    return cedente;
  }

  private static byte[] groupInPages(List<Boleto> boletos, File templatePersonalizado) {

    byte[] arq = null;
    BoletoViewer boletoViewer = new BoletoViewer(boletos.get(0));
    boletoViewer.setTemplate(templatePersonalizado);

    List<byte[]> boletosEmBytes = new ArrayList<byte[]>(boletos.size());

    // Adicionando os PDF, em forma de array de bytes, na lista.
    for (Boleto b : boletos) {
      boletosEmBytes.add(boletoViewer.setBoleto(b).getPdfAsByteArray());
    }
    try {
      // Criando o arquivo com os boletos da lista
      arq = mergeFilesInPages(boletosEmBytes);

    } catch (Exception e) {
      throw new IllegalStateException("Erro durante geração do PDF! Causado por "
          + e.getLocalizedMessage(), e);
    }

    return arq;
  }

  public static byte[] mergeFilesInPages(List<byte[]> pdfFilesAsByteArray)
      throws DocumentException, IOException {

    Document document = new Document();
    ByteArrayOutputStream byteOS = new ByteArrayOutputStream();

    PdfWriter writer = PdfWriter.getInstance(document, byteOS);

    document.open();

    PdfContentByte cb = writer.getDirectContent();
    float documentHeight = cb.getPdfDocument().getPageSize().getHeight();
    float positionAnterior = -1;

    // Para cada arquivo da lista, cria-se um PdfReader, responsável por ler o arquivo PDF e
    // recuperar informações dele.

    for (byte[] pdfFile : pdfFilesAsByteArray) {

      PdfReader reader = new PdfReader(pdfFile);

      // Faz o processo de mesclagem por página do arquivo, começando pela de número 1.
      for (int i = 1; i <= reader.getNumberOfPages(); i++) {

        // Importa a página do PDF de origem
        PdfImportedPage page = writer.getImportedPage(reader, i);
        if (positionAnterior == -1) {
          positionAnterior = documentHeight - page.getHeight();
        }
        float pagePosition = positionAnterior;

        /*
         * Se a altura restante no documento de destino form menor que a altura do documento,
         * cria-se uma nova página no documento de destino.
         */
        if (pagePosition < 0) {
          document.newPage();
          positionAnterior = documentHeight - page.getHeight();;
          pagePosition = positionAnterior;
        }

        // Adiciona a página ao PDF destino
        cb.addTemplate(page, 0, pagePosition);
        positionAnterior -= page.getHeight();
      }
    }

    byteOS.flush();
    document.close();

    byte[] arquivoEmBytes = byteOS.toByteArray();
    byteOS.close();

    return arquivoEmBytes;
  }

  private static String calcularDigitoDoNossoNumero(int carteira, String nossoNumero) {
    StringBuilder sb = new StringBuilder();
    sb.append(carteira).append(EMITENTE_BENEFICIARIO).append(nossoNumero);
    String numero = sb.reverse().toString();

    int multiplicador = 2;
    int soma = 0;

    for (int i = 0; i < numero.length(); i++) {
      int x = Integer.valueOf(Character.toString(numero.charAt(i)));

      // PASSO 1
      int valor = x * multiplicador++;

      if (multiplicador > 9) {
        multiplicador = 2;
      }
      // PASSO 2
      soma += valor;
    }

    // PASSO 3
    int resto = soma % 11;

    // PASSO 4
    int dv = 11 - resto;

    return Integer.toString(dv);
  }

}
