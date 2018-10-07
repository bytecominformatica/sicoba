package br.com.clairtonluz.sicoba.reports.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.comercial.Endereco;
import br.com.clairtonluz.sicoba.util.StringUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Component
public class ContratoReport {
    public static final String TEMPLATE = "/reports/comercial/contrato.pdf";
    public static final String DEST = "/tmp/contrato.pdf";
    public static final Font FONT_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

    public void contratoPDF(Contrato contrato) {
        try {
            File file = new File(DEST);
            file.getParentFile().mkdirs();
            createPdf(contrato);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createPdf(Contrato contrato) throws FileNotFoundException, DocumentException {
        // step 1
        Document document = new Document(PageSize.A4);
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(DEST));
        // step 3
        document.open();
        // step 4
        Paragraph title = new Paragraph("CONTRATO DE PRESTAÇÃO DE SERVIÇO", FontFactory.getFont(FontFactory.HELVETICA, 16));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        gerarPartes(contrato, document);
        // step 5
        document.close();
    }

    private void gerarPartes(Contrato contrato, Document document) throws DocumentException {
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Entre:"));
        document.add(Chunk.NEWLINE);
        document.add(getDadosContratantePF(contrato.getCliente()));
        document.add(getDadosContratada());
    }

    private Paragraph getDadosContratantePF(Cliente cliente) {
        Endereco endereco = cliente.getEndereco();
        Paragraph dadosContratante = new Paragraph();
        dadosContratante.add(new Phrase(cliente.getNome().toUpperCase(), FONT_BOLD));
        dadosContratante.add(new Phrase(", nacionalidade: Brasileiro" +
                ", estado civil: Solteiro" +
                ", RG " + cliente.getRg() +
                ", CPF " + StringUtil.formatarCpf(cliente.getCpfCnpj()) +
                ", residente em: " + endereco.getLogradouro() +
                ", Nº " + endereco.getNumero() +
                ", bairro " + endereco.getBairro().getNome() +
                ", CEP " + endereco.getCep() + ".\n" +
                "doravante denominado "
        ));
        dadosContratante.add(new Phrase("CONTRATANTE", FONT_BOLD));
        dadosContratante.add(new Phrase(",\n\n"));
        return dadosContratante;
    }

    private Paragraph getDadosContratada() {
        Paragraph dadosContratante = new Paragraph("a pessoa jurídica ");
        dadosContratante.add(new Phrase("AMANDA FERREIRA DA SILVA ARAUJO ME", FONT_BOLD));
        dadosContratante.add(new Phrase(", também denominada "));
        dadosContratante.add(new Phrase("BYTECOM INFORMÁTICA", FONT_BOLD));
        dadosContratante.add(new Phrase(", CNPJ n. 30.553.395/0001-10, com sede em: Rua 30, n. 977 Patrícia Gomes, Caucaia - CE, 61607-045.\n"));
        dadosContratante.add(new Phrase("neste ato representada, conforme poderes especialmente conferidos, por:\n"));
        dadosContratante.add(new Phrase("BAMANDA FERREIRA DA SILVA ARAUJO", FONT_BOLD));
        dadosContratante.add(new Phrase(", na qualidade de: Diretora Administrativa, CPF n. 605.774.803-40, Carteira de Identidade (RG) n. 20075615155, expedida por SSP-CE\n"));
        dadosContratante.add(new Phrase("a pessoa jurídica AMANDA FERREIRA DA SILVA ARAUJO ME, também denominada BYTECOM INFORMÁTICA, CNPJ n. 30.553.395/0001-10, com sede em: Rua 30, n. 977 Patrícia Gomes, Caucaia - CE, 61607-045.\n\n"));
        dadosContratante.add(new Phrase("doravante denominada "));
        dadosContratante.add(new Phrase("CONTRATADA", FONT_BOLD));
        dadosContratante.add(new Phrase(",\n"));
        return dadosContratante;
    }
}
