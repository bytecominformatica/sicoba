package br.com.clairtonluz.bytecom.model.service.estoque;

import br.com.clairtonluz.bytecom.model.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.repository.estoque.EquipamentoRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

public class EquipamentoService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private EquipamentoRepository equipamentoRepository;

    public Equipamento buscarPorId(Integer id) {
        return equipamentoRepository.findBy(id);
    }

    public List<Equipamento> buscarDisponiveisParaInstalacao() {
        return equipamentoRepository.findByDisponiveisInstalacao();
    }

    public List<Equipamento> buscarDisponiveisParaWifi() {
        return equipamentoRepository.findByDisponiveisWifi();
    }

    public List<Equipamento> buscarTodos() {
        return equipamentoRepository.findAll();
    }


    public Equipamento buscarPorMac(String mac) {
        return equipamentoRepository.findOptionalByMac(mac);
    }

    @Transactional
    public Equipamento save(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    @Transactional
    public void remove(Integer id) {
        Equipamento e = equipamentoRepository.findBy(id);
        equipamentoRepository.remove(e);
    }
}
