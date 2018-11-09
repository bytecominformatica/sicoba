package br.com.clairtonluz.sicoba.service.financeiro.nf.syncnfe;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.*;
import br.com.clairtonluz.sicoba.repository.financeiro.nf.NFeItemRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.nf.NFeItemSpec;
import br.com.clairtonluz.sicoba.repository.financeiro.nf.NFeRepository;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.FileUtils;
import br.com.clairtonluz.sicoba.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NFe.MODELO_21;


@Service
public class NFeService {

    public static final double PORCENTAGEM_VALOR_APROXIMADO_TRIBUTOS_FEDERAL = 13.45d;
    public static final double PORCENTAGEM_VALOR_APROXIMADO_TRIBUTOS_ESTADUAL = 10.89d;
    public static final double PORCENTAGEM_VALOR_APROXIMADO_TRIBUTOS_MUNICIPAL = 4.64d;
    private static final double ALICOTA_ICMP_ATE_180_MIL = 2.04d;
    private static final String SPED_CODIGO_MUNICIPIO_CAUCAIA = "2303709";
    private final NFeRepository nFeRepository;
    private final NFeItemRepository nFeItemRepository;

    public NFeService(NFeRepository nFeRepository, NFeItemRepository nFeItemRepository) {
        this.nFeRepository = nFeRepository;
        this.nFeItemRepository = nFeItemRepository;
    }

    @Transactional
    public List<NFe> generateNotas(List<Charge> charges) {
        for (Charge charge : charges) {
            if (nFeItemRepository.existsByCharge(charge))
                throw new ConflitException("Já existe uma nota fiscal para a cobrança " + charge.getId());

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

            if (charge.getCliente().getRg() == null || charge.getCliente().getRg().trim().isEmpty())
                throw new BadRequestException("Cliente " + charge.getCliente().getNome() + " não possui RG ou IE, esse campo é obrigatório para a nota fiscal.");

            if (charge.getCliente().getCpfCnpj().length() == 11) {
                nfe.setCpf(charge.getCliente().getCpfCnpj());
                nfe.setRg(charge.getCliente().getRg());
                nfe.setCfop(NFe.CFOP_PF);
            } else {
                nfe.setCnpj(charge.getCliente().getCpfCnpj());
                nfe.setIe(charge.getCliente().getRg());
                nfe.setCfop(NFe.CFOP_PJ);
            }

            nfe.setDiaDeVencimento(charge.getExpireAt().getDayOfMonth());
            nfe.setModelo(MODELO_21);
            nfe.setTelefone(charge.getCliente().getFoneTitular());
            nfe.setEmail(charge.getCliente().getEmail());
            nfe.setTipoAssinante(TipoAssinante.RESIDENCIAL_OU_PESSOA_FISICA);
            nfe.setTipoUtilizacao(TipoUtilizacao.PROVIMENTO_DE_INTERNET);
            nfe.setDataEmissao(charge.getExpireAt());
            nfe.setDataPrestacao(charge.getExpireAt());
            nfe.setObservacao(null);
            nfe.setCodigoMunicipio(SPED_CODIGO_MUNICIPIO_CAUCAIA);


            NfeItem nfeItem = new NfeItem();
            nfeItem.setCharge(charge);
            nfeItem.setClassificacaoServico(ClassificacaoServico.ASSINATURA_DE_SERVICOS_DE_PROVIMENTO_DE_ACESSO_A_INTERNET);
            nfeItem.setDescricao(charge.getDescription());

            Double valorUnitario = charge.getValue();
            double outrosValores = 0d;
            double desconto = 0d;
            Double paidValue = charge.getPaidValue() == null ? 0d : charge.getPaidValue();

            if (valorUnitario < paidValue) {
                outrosValores = paidValue - valorUnitario;
            } else {
                desconto = valorUnitario - paidValue;
            }

            double baseDeCalculo = valorUnitario + outrosValores - desconto;

            nfeItem.setValorUnitario(valorUnitario);
            nfeItem.setDesconto(desconto);
            nfeItem.setOutrosValores(outrosValores);
            nfeItem.setBc(baseDeCalculo);
            nfeItem.setUnidade("UN");
            nfeItem.setIcms(baseDeCalculo * ALICOTA_ICMP_ATE_180_MIL / 100);
            nfeItem.setAliquotaIcms(ALICOTA_ICMP_ATE_180_MIL);
            nfeItem.setAliquotaReducao(0d);
            nfeItem.setQuantidadeContratada(1.0);
            nfeItem.setQuantidadeFornecida(1.0);
            nfeItem.setValoresIsentos(0d);
            nfeItem.setValorAproximadoTributosFederal(baseDeCalculo * PORCENTAGEM_VALOR_APROXIMADO_TRIBUTOS_FEDERAL / 100);
            nfeItem.setValorAproximadoTributosEstadual(baseDeCalculo * PORCENTAGEM_VALOR_APROXIMADO_TRIBUTOS_ESTADUAL / 100);
            nfeItem.setValorAproximadoTributosMunicipal(baseDeCalculo * PORCENTAGEM_VALOR_APROXIMADO_TRIBUTOS_MUNICIPAL / 100);

            nFeRepository.save(nfe);
            nfeItem.setNfe(nfe);
            nFeItemRepository.save(nfeItem);
        }
        return null;
    }

    public void generateFiles(@NonNull List<NfeItem> nfeItemList, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=\"syncnfe.zip\"");
        generateFiles(nfeItemList, response.getOutputStream());
    }

    private void generateFiles(@NonNull List<NfeItem> nfeItemList, OutputStream outputStream) {
        AtomicInteger index = new AtomicInteger(1);
        StringBuilder master = new StringBuilder();
        StringBuilder detail = new StringBuilder();
        nfeItemList.forEach(nfeItem -> {
            master.append(gerarMaster(index.get(), nfeItem.getNfe()));
            detail.append(gerarDetail(index.getAndIncrement(), nfeItem));
        });

        InputStream masterIn = new ByteArrayInputStream(master.toString().getBytes(StandardCharsets.UTF_8));
        InputStream detailIn = new ByteArrayInputStream(detail.toString().getBytes(StandardCharsets.UTF_8));
        File masterFile = FileUtils.writeInDisk(masterIn, "/tmp/master.txt");
        File detailFile = FileUtils.writeInDisk(detailIn, "/tmp/detail.txt");

        byte[] masterAndDetailBytes = FileUtils.zipFiles(Arrays.asList(masterFile, detailFile));
        File masterAndDetailZipped = FileUtils.writeInDisk(new ByteArrayInputStream(masterAndDetailBytes), "/tmp/syncnfe.zip");
        FileUtils.downloadFile(masterAndDetailZipped.getPath(), outputStream);

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
                nFe.getClienteId() + "|" +
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

    public List<NfeItem> findItensByDatePrestacao(LocalDate begin, LocalDate end, StatusCharge status, Integer gerencianetAccountId) {
        Specification<NfeItem> where = Specification.where(NFeItemSpec.dataPrestacaoBetween(begin, end));
        if (status != null)
            where = where.and(NFeItemSpec.statusChargeEqual(status));
        if (gerencianetAccountId != null)
            where = where.and(NFeItemSpec.gerencianetAccountIdEqual(gerencianetAccountId));
        return nFeItemRepository.findAll(where);
    }

    @Transactional
    public void deleteAll(List<Integer> nfeItemIdList) {
        List<NfeItem> nfeItemList = nFeItemRepository.findAllById(nfeItemIdList);
        List<NFe> nFeList = nfeItemList.stream().map(NfeItem::getNfe).collect(Collectors.toList());
        nFeItemRepository.deleteAll(nfeItemList);
        nFeRepository.deleteAll(nFeList);
    }
}
