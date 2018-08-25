package br.com.clairtonluz.sicoba.service.financeiro.syncnfe;

import br.com.clairtonluz.sicoba.model.entity.comercial.TipoPessoa;
import br.com.clairtonluz.sicoba.model.entity.financeiro.syncnfe.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class SyncNFeServiceTest {

    private SyncNFeService syncNFeService = new SyncNFeService();
//    private List<NFe> notaList;

//    @Before
//    public void setUp() {
//        createNotas();
//    }

    @Test
    public void gerarMaster() {
        NFe nFe = criarNfe();
        String masterExpected = "1|1468|RODRIGO JOSE ZAMIATOWSKI|R. PROJETADA E|000|ULTIMA RUA DO LADO DE CIMA|NOVO HORIZONTE|CAPINZAL|SC|89665-000|||013.285.380-95|2091770319|20|21|5307|(49)3555-0000|sem@sem.com|0000001468|3|4|25/08/2018|26/08/2018|123|Nota de teste||\n";
        String masterActual = syncNFeService.gerarMaster(1, nFe);
        System.out.println(masterActual);
        Assert.assertEquals("master", masterExpected, masterActual);
    }

    @Test
    public void gerarDetail() {
        NfeItem nfeItem = criarNfeItem();
        String detailActual = syncNFeService.gerarDetail(1, nfeItem);
        String detailExpected = "1|104|SERVICO DE ACESSO A INTERNET|49,00|0|0|UN|1|1|0|104|0|0|0|0|0|0|0|\n";
        Assert.assertEquals("detail", detailExpected, detailActual);
    }


    private NFe criarNfe() {
        NFe nFe = new NFe();
        nFe.setCodigoConsumidor(1468);
        nFe.setNome("RODRIGO JOSE ZAMIATOWSKI");
        nFe.setLogradouro("R. PROJETADA E");
        nFe.setNumero("000");
        nFe.setComplemento("ULTIMA RUA DO LADO DE CIMA");
        nFe.setBairo("NOVO HORIZONTE");
        nFe.setCidade("CAPINZAL");
        nFe.setUf("SC");
        nFe.setCep("89665-000");
        nFe.setCnpj(null);
        nFe.setIe(null);
        nFe.setCpf("013.285.380-95");
        nFe.setRg("2091770319");
        nFe.setDiaDeVencimento(20);
        nFe.setModelo(NFe.MODELO_21);
        nFe.setCfop(TipoPessoa.PF.getCfop());
        nFe.setTelefone("(49)3555-0000");
        nFe.setEmail("sem@sem.com");
        nFe.setTipoAssinante(TipoAssinante.RESIDENCIAL_OU_PESSOA_FISICA);
        nFe.setTipoUtilizacao(TipoUtilizacao.PROVIMENTO_DE_INTERNET);
        nFe.setDataEmissao(LocalDate.of(2018, Month.AUGUST, 25));
        nFe.setDataPrestacao(LocalDate.of(2018, Month.AUGUST, 26));
        nFe.setId(123);
        nFe.setObservacao("Nota de teste");
        nFe.setCodigoMunicipio(null);
        return nFe;
    }

    private NfeItem criarNfeItem() {
        NfeItem nfeItem = new NfeItem();
        nfeItem.setClassificacaoServico(ClassificacaoServico.ASSINATURA_DE_SERVICOS_DE_PROVIMENTO_DE_ACESSO_A_INTERNET);
        nfeItem.setDescricao("SERVICO DE ACESSO A INTERNET");
        nfeItem.setValorUnitario(50d);
        nfeItem.setIcms(0d);
        nfeItem.setAliquotaReducao(0d);
        nfeItem.setUnidade("UN");
        nfeItem.setQuantidadeContratada(1d);
        nfeItem.setQuantidadeFornecida(1d);
        nfeItem.setAliquotaIcms(0d);
        nfeItem.setBc(0d);
        nfeItem.setValoresIsentos(0d);
        nfeItem.setOutrosValores(0d);
        nfeItem.setDesconto(0d);
        nfeItem.setValorAproximadoTributosFederal(0d);
        nfeItem.setValorAproximadoTributosEstadual(0d);
        nfeItem.setValorAproximadoTributosMunicipal(0d);

        return nfeItem;
    }


//    private void createNotas() {
//        notaList = new ArrayList<>();
//        NFe nFe = criarNfe();
//        notaList.add(nFe);
//
//        nFe = new NFe();
//        nFe.setCodigoConsumidor(2691);
//        nFe.setNome("MORGANA ALVES");
//        nFe.setLogradouro("R.FRANCISCO HELT");
//        nFe.setNumero("618");
//        nFe.setComplemento(null);
//        nFe.setBairo("NOVO HORIZONTE");
//        nFe.setCidade("CAPINZAL");
//        nFe.setUf("SC");
//        nFe.setCep("89665-000");
//        nFe.setCnpj(null);
//        nFe.setIe(null);
//        nFe.setCpf("013.285.380-95");
//        nFe.setRg("2091770319");
//        nFe.setDiaDeVencimento(20);
//        nFe.setModelo(NFe.MODELO_21);
//        nFe.setCfop(TipoPessoa.PF.getCfop());
//        nFe.setTelefone("(49)3555-0000");
//        nFe.setEmail("sem@sem.com");
//        nFe.setTipoAssinante(TipoAssinante.RESIDENCIAL_OU_PESSOA_FISICA);
//        nFe.setTipoUtilizacao(TipoUtilizacao.PROVIMENTO_DE_INTERNET);
//        nFe.setDataEmissao(LocalDate.of(2018, Month.AUGUST, 25));
//        nFe.setDataPrestacao(LocalDate.of(2018, Month.AUGUST, 26));
//        nFe.setId(123);
//        nFe.setObservacao("Nota de teste");
//        nFe.setCodigoMunicipio(null);
//        notaList.add(nFe);
//    }

}