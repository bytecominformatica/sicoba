package br.com.clairtonluz.sicoba.reports.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.comercial.Endereco;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.NumberUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Component
public class ContratoReport {
    public static final String DEST = "/tmp/contrato.pdf";
    public static final Font BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

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
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();
        Paragraph title = new Paragraph("CONTRATO DE PRESTAÇÃO DE SERVIÇO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        gerarPartes(contrato, document);

        document.add(new Paragraph("\nfirma-se o presente contrato de prestação de serviço, conforme as seguintes cláusulas.\n\n"));
        document.add(new Paragraph("CLÁUSULA 1ª - DO OBJETO\n\n", BOLD));
        document.add(new Phrase("Por meio deste contrato, a "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" se compromete a prestar ao "));
        document.add(new Phrase("CONTRATANTE ", BOLD));
        document.add(new Phrase("os seguintes serviços:\n\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("Fornecimento de serviço de internet banda larga;\n\n"));
        document.add(new Phrase("Parágrafo único. Os serviços descritos acima serão prestados com total autonomia, liberdade de horário, sem pessoalidade e sem qualquer subordinação ao "));
        document.add(new Phrase("CONTRATANTE.\n\n", BOLD));
        document.add(new Paragraph("CLÁUSULA 2ª - DO PRAZO\n\n", BOLD));
        document.add(new Phrase("O presente contrato tem prazo de: "));
        document.add(new Phrase("1 ano", BOLD));
        document.add(new Phrase(", com início em "));
        document.add(new Phrase(contrato.getDataInstalacao().format(DateUtil.DATE_PT_BR), BOLD));
        document.add(new Phrase(".\n\n" +
                "§ 1º. Findo o prazo estipulado, o contrato será automaticamente renovado, caso nenhuma das partes se manifeste contra.\n" +
                "§ 2º. Ao final deste prazo, o contrato poderá ser renovado, por igual ou inferior período de tempo.\n\n\n"));
        document.add(new Paragraph("CLÁUSULA 3ª - DA RETRIBUIÇÃO\n\n", BOLD));
        document.add(new Phrase("Em contrapartida aos serviços prestados, a "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" receberá a "));
        document.add(new Phrase("quantia mensal de R$ " +
                NumberUtil.formatCurrence(contrato.getPlano().getValor()) +
                " (" +
                NumberUtil.numberToWordReal(contrato.getPlano().getValor()) +
                ")", BOLD));
        document.add(new Phrase(", a ser paga "));
        document.add(new Phrase("até o dia 5 (cinco)", BOLD));
        document.add(new Phrase(" do mês subsequente ao vencido.\n" +
                "Parágrafo único. Em caso de mora no pagamento, será aplicada multa de "));
        document.add(new Phrase("6% (seis por cento)", BOLD));
        document.add(new Phrase(" sobre o valor devido, bem como juros de "));
        document.add(new Phrase("0,33% (zero vírgula trinta e três por cento", BOLD));
        document.add(new Phrase(", por dia de atraso.\n\n"));
        document.add(new Paragraph("CLÁUSULA 4ª - DAS OBRIGAÇÕES DA CONTRATADA\n\n", BOLD));
        document.add(new Phrase("São obrigações da "));
        document.add(new Phrase("CONTRATADA:\n\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("I. prestar, com a devida dedicação e seriedade e da forma e do modo ajustados, os serviços descritos neste contrato;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("II. respeitar as normas, as especificações técnicas e as condições de segurança aplicáveis à espécie de serviços prestados;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("III. fornecer as notas fiscais referentes aos pagamentos efetuados pelo "));
        document.add(new Phrase("CONTRATANTE\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("IV. responsabilizar-se pelos atos e omissões praticados por seus subordinados, bem como por quaisquer danos que os mesmos venham a sofrer ou causar para o "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(" ou terceiros;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("V. arcar devidamente, nos termos da legislação trabalhista, com a remuneração e demais verbas laborais devidas a seus subordinados, inclusive encargos fiscais e previdenciários referentes às relações de trabalho;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("VI. arcar com todas as despesas de natureza tributária decorrentes dos serviços especificados neste contrato;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("VII. cumprir todas as determinações impostas pelas autoridades públicas competentes, referentes a estes serviços;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("VIII. manter sigilosas, mesmo após findo este contrato, as informações privilegiadas de qualquer natureza às quais tenha acesso em virtude da execução destes serviços;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("IX. providenciar todos os meios e os equipamentos necessários à correta execução do serviço.\n\n"));
        document.add(new Paragraph("CLÁUSULA 5ª - DAS OBRIGAÇÕES DO CONTRATANTE\n\n", BOLD));
        document.add(new Phrase("São obrigações do "));
        document.add(new Phrase("CONTRATANTE:\n\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("I. fornecer todas as informações necessárias à realização dos serviços, inclusive especificando os detalhes e a forma de como eles devem ser entregues;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("II. efetuar o pagamento, nas datas e nos termos definidos neste contrato;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("III. comunicar imediatamente à "));
        document.add(new Phrase("CONTRATADA ", BOLD));
        document.add(new Phrase("sobre eventuais reclamações feitas contra seus subordinados, assim como sobre danos por eles causados.\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("IV. Ser o único e exclusivo responsável pelo conteúdo em sua área de Home Page, pelo backup da mesma, bem como, das mensagens transmitidas por ele ou para ele, sob sua autorização, especialmente aquelas que venham a ofender dispositivo ou princípio legal, ético ou moral, desde já, sendo o único responsável por quaisquer informações distribuídas na rede ou quaisquer outros prejuízos que venha causar a "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" ou a terceiros.\n\n"));
        document.add(new Paragraph("CLÁUSULA 6ª - DA RESCISÃO\n\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("A qualquer momento, poderão as partes rescindir este contrato, desde que avise previamente à outra parte, com antecedência de 8 (oito) dias:\n\n" +
                "§ 1º. A rescisão sem justa causa por parte da "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" não lhe retira o direito ao recebimento das retribuições já vencidas, porém sujeita-lhe ao pagamento de perdas e danos ao "));
        document.add(new Phrase("CONTRATANTE.\n\n", BOLD));
        document.add(new Phrase("§ 2º. A rescisão sem justa causa por parte do "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(" obriga-lhe a pagar à "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" por inteiro as retribuições já vencidas.\n\n" +
                "§ 3º. Não serão aplicáveis os prazos fixados nesta cláusula às rescisões por justa causa.\n\n" +
                "§ 4º. A rescisão com justa causa, realizada por qualquer uma das partes, não exime o "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(" do pagamento das retribuições já vencidas.\n\n" +
                "§ 5º. A rescisão com justa causa por parte do "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(" obriga a devolução pela "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" dos valores já pagos referentes a serviços não desenvolvidos.\n\n"));
        document.add(new Paragraph("CLÁUSULA 7ª - DA EXTINÇÃO DO CONTRATO\n\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("O presente contrato de prestação de serviço extingue-se mediante a ocorrência de uma das seguintes hipóteses:\n\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("I. morte de qualquer das partes;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("II. pelo atraso do pagamento por mais de 30 dias corridos;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("III. conclusão do serviço;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("IV. rescisão do contrato mediante aviso prévio, por inadimplemento de qualquer das partes ou pela impossibilidade da continuação do contrato, motivada por força maior.\n\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("Parágrafo único. Ainda que a extinção do contrato tenha sido realizada pela "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" sem justo motivo, esta terá direito a exigir do "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(" a declaração de que o contrato está findo.\n\n\n\n"));
        document.add(new Paragraph("CLÁUSULA 8ª - DO DESCUMPRIMENTO\n\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("O descumprimento de quaisquer das obrigações e das cláusulas fixadas neste contrato, seja pelo "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase("ou pela "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(", ensejará a sua imediata rescisão, por justa causa, e sujeitará o infrator ao pagamento de multa correspondente a o valor da última retribuição mensal atualizada, abatida proporcionalmente conforme o tempo restante de cumprimento do contrato.\n\n"));
        document.add(new Paragraph("CLÁUSULA 9ª - DAS DISPOSIÇÕES GERAIS\n\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("Ao assinarem este instrumento, as partes concordam ainda que:\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("a) nem o "));
        document.add(new Phrase("CONTRATANTE ", BOLD));
        document.add(new Phrase("poderá transferir a outrem o direito aos serviços ajustados, nem a "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(", sem aprazimento da outra parte, dar substituto que os preste;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("b) A "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" reserva o direito de suspender, alterar, acrescentar ou extinguir qualquer tipo de serviço que, em função de sua utilização, venha causar dano ao sistema ou venha a ser reprovado por circunstâncias operacionais, ou ainda, de facilidade que seja ou que possa ser disponibilizada ao "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(", mediante aviso prévio de 7 (sete) dias e com a conseqüente eliminação da taxa correspondente ao serviço específico, caso haja;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("c) a mera tolerância pelas partes com relação ao descumprimento de quaisquer dos termos ajustados neste contrato não deverá ser considerada como desistência de sua exigência;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("d) o presente contrato não gera direito de exclusividade entre as partes;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("e) A "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" poderá suspender os serviços prestados temporariamente em caso de atraso no pagamento por mais de 15 dias;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("f) poderá, ainda, o "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(" contratar um terceiro para realizar os mesmos serviços descritos neste contrato, a título de complementaridade;\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("g) A "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" fiscalizará a conduta do "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(" no uso da rede, podendo notificá-lo caso detecte irregularidade. Se o "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase("não adotar as providências e medidas solicitadas pela CONTRATADA, esta poderá, a qualquer momento e a seu critério, independentemente de ação ou ordem judicial, suspender temporariamente ou definitivamente a prestação de serviço ao "));
        document.add(new Phrase("CONTRATANTE;\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("h) A "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(" não será, em hipótese alguma, responsável pela interrupção ou suspensão da conexão à rede internet, e dos danos decorrentes, nos casos de: \n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("· interrupção no fornecimento de energia elétrica para o sistema da sua empresa, falhas no sistema de transmissão ou de roteamento no acesso à internet; \n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("· desligamento ou interrupção temporária do sistema, decorrente de reparos ou manutenção das redes elétrica e telefônica externas; \n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("· incompatibilidade dos sistemas do "));
        document.add(new Phrase("CONTRATANTE", BOLD));
        document.add(new Phrase(" com os da "));
        document.add(new Phrase("CONTRATADA;\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("· interrupção ou suspensão de prestação de serviços decorrentes de motivos de força maior, caso fortuito ou ação de terceiros, que ocorram independentemente da vontade da "));
        document.add(new Phrase("CONTRATADA", BOLD));
        document.add(new Phrase(", assim como interrupção ou cancelamento, por acidente natural ou por qualquer outro motivo, dos serviços básicos (acesso à Rede Internet através das linhas internacionais da Rede Nacional de Pesquisas, EMBRATEL e/ou outros, conexões LCPD e de linhas telefônicas da TELEMAR).\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("i) eventuais alterações deste contrato deverão ser realizadas por meio de termo aditivo, devidamente assinado pelas partes e por 02 (duas) testemunha.\n\n"));
        document.add(new Paragraph("CLÁUSULA 10ª - FORO\n\n", BOLD));
        document.add(Chunk.TABBING);
        document.add(new Phrase("As partes elegem o foro da comarca de CAUCAIA - CE, para dirimir quaisquer dúvidas oriundas deste Contrato de Prestação de Serviços e do Termo de Adesão e de eventuais comunicações e/ou aditamentos, renunciando expressamente a outro que tenha ou venha a ter, por mais privilegiado que seja.\n\n"));
        document.add(Chunk.TABBING);
        document.add(new Phrase("E por estarem, assim, de justo acordo, as partes assinam este instrumento em 02 (duas) vias de idêntico conteúdo e forma, na presença de 02 (duas) testemunha, abaixo arroladas.\n\n\n"));
        document.add(assinaturas(contrato));

        document.close();
    }

    private Paragraph assinaturas(Contrato contrato) {
        Paragraph assinaturas = new Paragraph();
        Paragraph localEAssinatura = new Paragraph("___________________, ______ de ____________________ de ________\n" +
                "(Local e data de assinatura)\n\n\n");
        localEAssinatura.setAlignment(Element.ALIGN_CENTER);
        assinaturas.add(localEAssinatura);
        assinaturas.add(new Phrase("CONTRATANTE:\n\n", BOLD));
        Paragraph assinaturaContratante = new Paragraph("________________________________________________________\n" +
                contrato.getCliente().getNome().toUpperCase(), BOLD);
        assinaturaContratante.setAlignment(Element.ALIGN_CENTER);
        assinaturas.add(assinaturaContratante);
        assinaturas.add(new Phrase("\nCONTRATADA:\n\n", BOLD));
        Paragraph assinaturaContrada = new Paragraph();
        assinaturaContrada.add(new Phrase("________________________________________________________\n" +
                "AMANDA FERREIRA DA SILVA ARAUJO\n", BOLD));
        assinaturaContrada.add(new Phrase("neste ato representando a pessoa jurídica "));
        assinaturaContrada.add(new Phrase("AMANDA FERREIRA DA SILVA ARAUJO ME\n\n", BOLD));
        assinaturaContrada.setAlignment(Element.ALIGN_CENTER);
        assinaturas.add(assinaturaContrada);
        assinaturas.add(new Paragraph("TESTEMUNHAS:\n\n", BOLD));
        Paragraph testemunha = new Paragraph("________________________________________________________\n" +
                "(assinatura)\n" +
                "Nome completo:___________________________________________________\n" +
                "CPF n.:_______________________");
        testemunha.setAlignment(Element.ALIGN_CENTER);
        assinaturas.add(testemunha);
        assinaturas.add(new Paragraph("\n"));
        assinaturas.add(testemunha);
        return assinaturas;
    }

    private void gerarPartes(Contrato contrato, Document document) throws DocumentException {
        document.add(new Paragraph("\n\nEntre:\n\n"));
        document.add(getDadosContratantePF(contrato.getCliente()));
        document.add(new Paragraph("\ne:\n\n"));
        document.add(getDadosContratada());
    }

    private Paragraph getDadosContratantePF(Cliente cliente) {
        Endereco endereco = cliente.getEndereco();
        Paragraph dadosContratante = new Paragraph();
        dadosContratante.add(new Phrase(cliente.getNome().toUpperCase(), BOLD));
        dadosContratante.add(new Phrase(", nacionalidade: Brasileiro" +
                ", estado civil: Solteiro" +
                ", RG " + cliente.getRg() +
                ", CPF " + StringUtil.formatarCpf(cliente.getCpfCnpj()) +
                ", residente em: " + endereco.getLogradouroNumeroComplemento() +
                ", bairro " + endereco.getBairro().getNome() +
                ", CEP " + StringUtil.formatarCep(endereco.getCep()) + ".\n\ndoravante denominado "
        ));
        dadosContratante.add(new Phrase("CONTRATANTE,", BOLD));
        return dadosContratante;
    }

    private Paragraph getDadosContratada() {
        Paragraph dadosContratante = new Paragraph("a pessoa jurídica ");
        dadosContratante.add(new Phrase("AMANDA FERREIRA DA SILVA ARAUJO ME", BOLD));
        dadosContratante.add(new Phrase(", também denominada "));
        dadosContratante.add(new Phrase("BYTECOM INFORMÁTICA", BOLD));
        dadosContratante.add(new Phrase(", CNPJ n. 30.553.395/0001-10, com sede em: Rua 30, n. 977 Patrícia Gomes, Caucaia - CE, 61607-045. "));
        dadosContratante.add(new Phrase("Neste ato representada, conforme poderes especialmente conferidos, por: "));
        dadosContratante.add(new Phrase("AMANDA FERREIRA DA SILVA ARAUJO", BOLD));
        dadosContratante.add(new Phrase(", na qualidade de: Diretora Administrativa, CPF n. 605.774.803-40, Carteira de Identidade (RG) n. 20075615155, expedida por SSP-CE\n\n"));
        dadosContratante.add(new Phrase("doravante denominada "));
        dadosContratante.add(new Phrase("CONTRATADA", BOLD));
        dadosContratante.add(new Phrase(",\n"));
        return dadosContratante;
    }
}
