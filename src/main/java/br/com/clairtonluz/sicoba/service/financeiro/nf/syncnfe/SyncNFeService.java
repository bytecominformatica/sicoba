package br.com.clairtonluz.sicoba.service.financeiro.nf.syncnfe;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.*;
import br.com.clairtonluz.sicoba.repository.financeiro.nf.NFeItemRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.nf.NFeRepository;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NFe.MODELO_21;


@Service
public class SyncNFeService {

    private final NFeRepository nFeRepository;
    private final NFeItemRepository nFeItemRepository;

    public SyncNFeService(NFeRepository nFeRepository, NFeItemRepository nFeItemRepository) {
        this.nFeRepository = nFeRepository;
        this.nFeItemRepository = nFeItemRepository;
    }

    @Transactional
    public List<NFe> generateNotas(List<Charge> charges) {
        for (Charge charge : charges) {
            NFe nfe = new NFe();
            nfe.setClienteId(charge.getCliente().getId());
            nfe.setNome(charge.getCliente().getNome());
            nfe.setLogradouro(charge.getCliente().getEndereco().getLogradouro());
            nfe.setNumero(charge.getCliente().getEndereco().getNumero());
            nfe.setComplemento(charge.getCliente().getEndereco().getComplemento());
            nfe.setBairo(charge.getCliente().getEndereco().getBairro().getNome());
            nfe.setCidade(charge.getCliente().getEndereco().getBairro().getCidade().getNome());
            nfe.setUf(charge.getCliente().getEndereco().getBairro().getCidade().getEstado().getUf());
            nfe.setCep(charge.getCliente().getEndereco().getCep());
            if (charge.getCliente().getCpfCnpj().length() == 11) {
                nfe.setCpf(charge.getCliente().getCpfCnpj());
            } else {
                nfe.setCnpj(charge.getCliente().getCpfCnpj());
            }
            nfe.setIe(null);
            nfe.setRg(charge.getCliente().getRg());
            nfe.setDiaDeVencimento(charge.getExpireAt().getDayOfMonth());
            nfe.setModelo(MODELO_21);
            nfe.setCfop(0);
            nfe.setTelefone(charge.getCliente().getFoneTitular());
            nfe.setEmail(charge.getCliente().getEmail());
            nfe.setTipoAssinante(TipoAssinante.RESIDENCIAL_OU_PESSOA_FISICA);
            nfe.setTipoUtilizacao(TipoUtilizacao.PROVIMENTO_DE_INTERNET);
            nfe.setDataEmissao(LocalDate.now());
            nfe.setDataPrestacao(LocalDate.now());
            nfe.setObservacao(null);
            nfe.setCodigoMunicipio(null);

            NfeItem nfeItem = new NfeItem();
            nfeItem.setCharge(charge);
            nfeItem.setClassificacaoServico(ClassificacaoServico.ASSINATURA_DE_SERVICOS_DE_PROVIMENTO_DE_ACESSO_A_INTERNET);
            nfeItem.setDescricao(charge.getDescription());
            nfeItem.setValorUnitario(charge.getValue());
            nfeItem.setIcms(0d);
            nfeItem.setAliquotaReducao(0d);
            nfeItem.setUnidade("UN");
            nfeItem.setQuantidadeContratada(1.0);
            nfeItem.setQuantidadeFornecida(1.0);
            nfeItem.setAliquotaIcms(0d);
            nfeItem.setBc(0d);
            nfeItem.setValoresIsentos(0d);
            nfeItem.setOutrosValores(0d);
            nfeItem.setDesconto(charge.getDiscount());
            nfeItem.setValorAproximadoTributosFederal(0d);
            nfeItem.setValorAproximadoTributosEstadual(0d);
            nfeItem.setValorAproximadoTributosMunicipal(0d);

            nFeRepository.save(nfe);
            nfeItem.setNfe(nfe);
            nFeItemRepository.save(nfeItem);
        }
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
                StringUtil.formatCurrence(nfeItem.getDesconto()) + "|" +
                StringUtil.formatCurrence(nfeItem.getValorAproximadoTributosFederal().orElse(0d)) + "|" +
                StringUtil.formatCurrence(nfeItem.getValorAproximadoTributosEstadual().orElse(0d)) + "|" +
                StringUtil.formatCurrence(nfeItem.getValorAproximadoTributosMunicipal().orElse(0d)) + "|" +
                "\n";
    }

    String gerarMaster(int sequencial, @NonNull NFe nFe) {
        return sequencial + "|" +
                nFe.getClienteId() + "|" +
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
                StringUtil.padLeft(nFe.getClienteId(), 10) + "|" +
                nFe.getTipoAssinante().getCodigo() + "|" +
                nFe.getTipoUtilizacao().getCodigo() + "|" +
                DateUtil.formatDate(nFe.getDataEmissao()) + "|" +
                DateUtil.formatDate(nFe.getDataPrestacao()) + "|" +
                nFe.getId() + "|" +
                nFe.getObservacao().orElse("") + "|" +
                nFe.getCodigoMunicipio().orElse("") + "|" +
                "\n";
    }


    public List<NFe> getAll() {
        return nFeRepository.findAll();
    }

    public List<NFe> busca() {
        return null;
    }

}
