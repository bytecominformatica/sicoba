package br.com.clairtonluz.sicoba.model.entity.financeiro.nf;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Optional;

@Entity
@Table(name = "nfe_item")
public class NfeItem extends BaseEntity {
    private ClassificacaoServico classificacaoServico; // 104
    private String descricao;
    private Double valorUnitario;
    private Double icms;// 0
    private Double aliquotaReducao; // 0
    private String unidade; // UN
    private Double quantidadeContratada; // 1
    private Double quantidadeFornecida; // 1
    private Double aliquotaIcms;// 0
    private Double bc;// 0
    private Double valoresIsentos;// 0
    private Double outrosValores;// 0
    private Double desconto;
    private Double valorAproximadoTributosFederal;// 0
    private Double valorAproximadoTributosEstadual;// 0
    private Double valorAproximadoTributosMunicipal;// 0

    // unique (charge_id)
    @OneToMany
    @JoinColumn(name = "charge_id")
    private Charge charge;

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
}
