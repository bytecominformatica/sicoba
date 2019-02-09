package br.com.clairtonluz.sicoba.model.entity.financeiro.nf;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntityGets;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.ChargeWithoutNfeItem;

import java.util.Optional;

public interface NfeItemWithoutNfeItemInCharge extends BaseEntityGets {

    public ClassificacaoServico getClassificacaoServico();

    public Double getValorUnitario();

    public Double getIcms();

    public Double getAliquotaReducao();

    public String getUnidade();

    public Double getQuantidadeContratada();

    public Double getQuantidadeFornecida();

    public Double getAliquotaIcms();

    public Optional<Double> getBc();

    public Optional<Double> getValoresIsentos();

    public Optional<Double> getOutrosValores();

    public Double getDesconto();

    public Optional<Double> getValorAproximadoTributosFederal();

    public Optional<Double> getValorAproximadoTributosEstadual();

    public Optional<Double> getValorAproximadoTributosMunicipal();

    public ChargeWithoutNfeItem getCharge();

    public NFe getNfe();
}
