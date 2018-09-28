package br.com.clairtonluz.sicoba.model.entity.financeiro.nf;

import br.com.clairtonluz.sicoba.model.entity.comercial.Endereco;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.util.Optional;

@Entity
@Table(name = "nfe_item")
public class NfeItem extends BaseEntity {
	@Column(name = "classificacao_servico")
    private ClassificacaoServico classificacaoServico; // 104
    private String descricao;
    private Double valorUnitario;
    private Double icms;// 0    
    @Column(name = "aliquota_reducao")
    private Double aliquotaReducao; // 0
    private String unidade; // UN
    @Column(name = "quantidade_contratada")
    private Double quantidadeContratada; // 1
    @Column(name = "quantidade_fornecida")
    private Double quantidadeFornecida; // 1
    @Column(name = "aliquota_icms")
    private Double aliquotaIcms;// 0
    private Double bc;// 0
    @Column(name = "valores_isentos")
    private Double valoresIsentos;// 0
    @Column(name = "outros_valores")
    private Double outrosValores;// 0
    private Double desconto;
    @Column(name = "valor_aproximado_tributos_federal")
    private Double valorAproximadoTributosFederal;// 0
    @Column(name = "valor_aproximado_tributos_estadual")
    private Double valorAproximadoTributosEstadual;// 0
    @Column(name = "valor_aproximado_tributos_municipal")
    private Double valorAproximadoTributosMunicipal;// 0

    // unique (charge_id)
    @ManyToOne
    @JoinColumn(name = "charge_id",unique=true)    
    private Charge charge; 
    
    @ManyToOne
    @JoinColumn(name = "nfe_id",unique=true)    
    private NFe nfe;        

    public ClassificacaoServico getClassificacaoServico() {
        return classificacaoServico;
    }

    public void setClassificacaoServico(ClassificacaoServico classificacaoServico) {
        this.classificacaoServico = classificacaoServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getIcms() {
        return icms;
    }

    public void setIcms(Double icms) {
        this.icms = icms;
    }

    public Double getAliquotaReducao() {
        return aliquotaReducao;
    }

    public void setAliquotaReducao(Double aliquotaReducao) {
        this.aliquotaReducao = aliquotaReducao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Double getQuantidadeContratada() {
        return quantidadeContratada;
    }

    public void setQuantidadeContratada(Double quantidadeContratada) {
        this.quantidadeContratada = quantidadeContratada;
    }

    public Double getQuantidadeFornecida() {
        return quantidadeFornecida;
    }

    public void setQuantidadeFornecida(Double quantidadeFornecida) {
        this.quantidadeFornecida = quantidadeFornecida;
    }

    public Double getAliquotaIcms() {
        return aliquotaIcms;
    }

    public void setAliquotaIcms(Double aliquotaIcms) {
        this.aliquotaIcms = aliquotaIcms;
    }

    public Optional<Double> getBc() {
        return Optional.ofNullable(bc);
    }

    public void setBc(Double bc) {
        this.bc = bc;
    }

    public Optional<Double> getValoresIsentos() {
        return Optional.ofNullable(valoresIsentos);
    }

    public void setValoresIsentos(Double valoresIsentos) {
        this.valoresIsentos = valoresIsentos;
    }

    public Optional<Double> getOutrosValores() {
        return Optional.ofNullable(outrosValores);
    }

    public void setOutrosValores(Double outrosValores) {
        this.outrosValores = outrosValores;
    }

    public Optional<Double> getDesconto() {
        return Optional.ofNullable(desconto);
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Optional<Double> getValorAproximadoTributosFederal() {
        return Optional.ofNullable(valorAproximadoTributosFederal);
    }

    public void setValorAproximadoTributosFederal(Double valorAproximadoTributosFederal) {
        this.valorAproximadoTributosFederal = valorAproximadoTributosFederal;
    }

    public Optional<Double> getValorAproximadoTributosEstadual() {
        return Optional.ofNullable(valorAproximadoTributosEstadual);
    }

    public void setValorAproximadoTributosEstadual(Double valorAproximadoTributosEstadual) {
        this.valorAproximadoTributosEstadual = valorAproximadoTributosEstadual;
    }

    public Optional<Double> getValorAproximadoTributosMunicipal() {
        return Optional.ofNullable(valorAproximadoTributosMunicipal);
    }

    public void setValorAproximadoTributosMunicipal(Double valorAproximadoTributosMunicipal) {
        this.valorAproximadoTributosMunicipal = valorAproximadoTributosMunicipal;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

	public NFe getNfe() {
		return nfe;
	}

	public void setNfe(NFe nfe) {
		this.nfe = nfe;
	}
    
    
}
