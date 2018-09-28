package br.com.clairtonluz.sicoba.service.financeiro.nf.syncnfe;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.ClassificacaoServico;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NFe;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItem;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.SyncNFeImportacao;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.TipoAssinante;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.TipoUtilizacao;
import br.com.clairtonluz.sicoba.repository.financeiro.nf.NFeItemRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.nf.NFeRepository;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;



@Service
public class SyncNFeService {
	
	@Autowired
    private NFeRepository nFeRepository;
	
	@Autowired
    private NFeItemRepository nFeItemRepository;

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
				}else {
					nfe.setCnpj(charge.getCliente().getCpfCnpj()); 
				} 	        		        		    
    		    nfe.setIe(null);  		        		    
    		    nfe.setRg(charge.getCliente().getRg());     		    
    		    nfe.setDiaDeVencimento(charge.getExpireAt().getDayOfMonth());
    		    nfe.setModelo( nfe.MODELO_21); 
    		    nfe.setCfop(0);
    		    nfe.setTelefone(charge.getCliente().getFoneTitular());
    		    nfe.setEmail(charge.getCliente().getEmail());    		   
    		    nfe.setCodigoConsumidor(charge.getCliente().getId());   		        		    
    		    nfe.setTipoAssinante(TipoAssinante.RESIDENCIAL_OU_PESSOA_FISICA);
    		    nfe.setTipoUtilizacao(TipoUtilizacao.PROVIMENTO_DE_INTERNET);    		   
    		    nfe.setDataEmissao(LocalDate.now());
    		    nfe.setDataPrestacao(LocalDate.now());    		   
    		    nfe.setObservacao(null);
    		    nfe.setCodigoMunicipio(null); 	      		    		   
    		    
    		    List<NfeItem> itens = new ArrayList<NfeItem>();  		        		    
    		   
    		    NfeItem nfeItem = new NfeItem();
    		    nfeItem.setCharge(charge);
    	    	nfeItem.setClassificacaoServico(ClassificacaoServico.ASSINATURA_DE_SERVICOS_DE_PROVIMENTO_DE_ACESSO_A_INTERNET);
    	    	nfeItem.setDescricao(charge.getDescription()); 
    	    	nfeItem.setValorUnitario(charge.getValue());
    	    	nfeItem.setIcms(0.0);
    	    	nfeItem.setAliquotaReducao(0.0); 
    	    	nfeItem.setUnidade("UN");
    	    	nfeItem.setQuantidadeContratada(1.0);
    	    	nfeItem.setQuantidadeFornecida(1.0);
    	    	nfeItem.setAliquotaIcms(0.0); 
    	    	nfeItem.setBc(0.0);
    	    	nfeItem.setValoresIsentos(0.0);
    	    	nfeItem.setOutrosValores(0.0);
    	    	nfeItem.setDesconto(charge.getDiscount());
    	    	nfeItem.setValorAproximadoTributosFederal(0.0);
    	    	nfeItem.setValorAproximadoTributosEstadual(0.0); 
    	    	nfeItem.setValorAproximadoTributosMunicipal(0.0);   	    	
    	    	
    	    	itens.add(nfeItem);
    	    	nfe.setItens(itens);
    	    	
    	    	nFeRepository.save(nfe);
    	    	
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
	
	
	public List<NFe> getAll() {        
        return nFeRepository.findAll();
    }
	
	public List<NFe> busca() {        
        return null;
    }

}
