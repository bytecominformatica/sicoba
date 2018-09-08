package br.com.clairtonluz.sicoba.service.financeiro.nf.syncnfe;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NFe;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItem;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.SyncNFeImportacao;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SyncNFeService {

    public List<NFe> generateNotas(List<Charge> charges) {
        return null;
    }

    public SyncNFeImportacao generateFiles(@NonNull List<NFe> notas) {
        SyncNFeImportacao syncNFeImportacao = new SyncNFeImportacao();
        AtomicInteger index = new AtomicInteger(1);
        StringBuilder master = new StringBuilder();
        StringBuilder detail = new StringBuilder();
        notas.forEach(nFe -> {
            nFe.getItens().forEach(nfeItem -> {
                master.append(gerarMaster(index.get(), nFe));
                detail.append(gerarDetail(index.getAndIncrement(), nfeItem));
            });
        });
        syncNFeImportacao.setMaster(master.toString());
        syncNFeImportacao.setDetail(detail.toString());
        return syncNFeImportacao;
    }

    String gerarDetail(int sequencial, @NonNull NfeItem nfeItem) {
        return sequencial + "|" +
                nfeItem.getClassificacaoServico().getCodigo() + "|" +
                nfeItem.getDescricao() + "|" +
                StringUtil.formatCurrence(nfeItem.getValorUnitario()) + "|" +
                StringUtil.formatCurrence(nfeItem.getIcms()) + "|" +
                StringUtil.formatCurrence(nfeItem.getAliquotaReducao()) + "|" +
                nfeItem.getUnidade() + "|" +
                StringUtil.formatCurrence(nfeItem.getQuantidadeContratada()) + "|" +
                StringUtil.formatCurrence(nfeItem.getQuantidadeFornecida()) + "|" +
                StringUtil.formatCurrence(nfeItem.getAliquotaIcms()) + "|" +
                StringUtil.padLeft(nfeItem.getClassificacaoServico().getCodigo(), 3) + "|" +
                StringUtil.formatCurrence(nfeItem.getBc().orElse(0d)) + "|" +
                StringUtil.formatCurrence(nfeItem.getValoresIsentos().orElse(0d)) + "|" +
                StringUtil.formatCurrence(nfeItem.getOutrosValores().orElse(0d)) + "|" +
                StringUtil.formatCurrence(nfeItem.getDesconto().orElse(0d)) + "|" +
                StringUtil.formatCurrence(nfeItem.getValorAproximadoTributosFederal().orElse(0d)) + "|" +
                StringUtil.formatCurrence(nfeItem.getValorAproximadoTributosEstadual().orElse(0d)) + "|" +
                StringUtil.formatCurrence(nfeItem.getValorAproximadoTributosMunicipal().orElse(0d)) + "|" +
                "\n";
    }

    String gerarMaster(int sequencial, @NonNull NFe nFe) {
        return sequencial + "|" +
                nFe.getCodigoConsumidor() + "|" +
                nFe.getNome() + "|" +
                nFe.getLogradouro() + "|" +
                nFe.getNumero() + "|" +
                nFe.getComplemento().orElse("") + "|" +
                nFe.getBairo() + "|" +
                nFe.getCidade() + "|" +
                nFe.getUf() + "|" +
                nFe.getCep() + "|" +
                nFe.getCnpj().orElse("") + "|" +
                nFe.getIe().orElse("") + "|" +
                nFe.getCpf().orElse("") + "|" +
                nFe.getRg().orElse("") + "|" +
                nFe.getDiaDeVencimento() + "|" +
                nFe.getModelo() + "|" +
                nFe.getCfop() + "|" +
                nFe.getTelefone() + "|" +
                nFe.getEmail().orElse("") + "|" +
                StringUtil.padLeft(nFe.getCodigoConsumidor(), 10) + "|" +
                nFe.getTipoAssinante().getCodigo() + "|" +
                nFe.getTipoUtilizacao().getCodigo() + "|" +
                DateUtil.formatDate(nFe.getDataEmissao()) + "|" +
                DateUtil.formatDate(nFe.getDataPrestacao()) + "|" +
                nFe.getId() + "|" +
                nFe.getObservacao().orElse("") + "|" +
                nFe.getCodigoMunicipio().orElse("") + "|" +
                "\n";
    }

}
