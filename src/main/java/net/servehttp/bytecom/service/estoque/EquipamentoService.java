package net.servehttp.bytecom.service.estoque;

import net.servehttp.bytecom.model.jpa.entity.estoque.Equipamento;
import net.servehttp.bytecom.model.jpa.entity.estoque.StatusEquipamento;
import net.servehttp.bytecom.model.jpa.entity.estoque.TipoEquipamento;
import net.servehttp.bytecom.model.jpa.estoque.EquipamentoJPA;
import net.servehttp.bytecom.model.jpa.extra.GenericoJPA;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class EquipamentoService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private EquipamentoJPA equipamentoJPA;
    @Inject
    private GenericoJPA jpa;

    public void remover(Equipamento e) {
        equipamentoJPA.remover(e);
    }

    public Equipamento buscarPorId(int id) {
        return jpa.buscarPorId(Equipamento.class, id);
    }

    public List<Equipamento> buscarEquipamentosInstalacaoNaoUtilizados() {
        return equipamentoJPA.buscarEquipamentosNaoUtilizados(TipoEquipamento.INSTALACAO, StatusEquipamento.OK);
    }

    public List<Equipamento> buscarEquipamentosWifiNaoUtilizados() {
        return equipamentoJPA.buscarEquipamentosNaoUtilizados(TipoEquipamento.WIFI, StatusEquipamento.OK);
    }

}
