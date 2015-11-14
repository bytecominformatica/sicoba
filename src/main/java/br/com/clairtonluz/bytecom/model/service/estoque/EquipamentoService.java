package br.com.clairtonluz.bytecom.model.service.estoque;

import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.StatusEquipamento;
import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.TipoEquipamento;
import br.com.clairtonluz.bytecom.model.jpa.estoque.EquipamentoJPA;
import br.com.clairtonluz.bytecom.model.jpa.extra.GenericoJPA;
import br.com.clairtonluz.bytecom.model.service.CrudService;

import javax.inject.Inject;
import java.util.List;

public class EquipamentoService extends CrudService {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private EquipamentoJPA equipamentoJPA;
    @Inject
    private GenericoJPA jpa;

    public void remove(Equipamento e) {
        equipamentoJPA.remove(e);
    }

    public Equipamento buscarPorId(int id) {
        return jpa.buscarPorId(Equipamento.class, id);
    }

    public List<Equipamento> buscarEquipamentosInstalacaoNaoUtilizados() {
        return equipamentoJPA.buscarNaoUtilizados(TipoEquipamento.INSTALACAO, StatusEquipamento.OK);
    }

    public List<Equipamento> buscarEquipamentosWifiNaoUtilizados() {
        return equipamentoJPA.buscarNaoUtilizados(TipoEquipamento.WIFI, StatusEquipamento.OK);
    }

    public List<Equipamento> buscarTodos() {
        return equipamentoJPA.findAll();
    }


    public Equipamento buscarPorMac(String mac) {
        return equipamentoJPA.buscarPorMac(mac);
    }
}
