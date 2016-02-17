package br.com.clairtonluz.sicoba.service.estoque;

import br.com.clairtonluz.sicoba.model.entity.estoque.Equipamento;
import br.com.clairtonluz.sicoba.repository.estoque.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public Equipamento buscarPorId(Integer id) {
        return equipamentoRepository.findOne(id);
    }

    public List<Equipamento> buscarDisponiveisParaInstalacao() {
        return equipamentoRepository.findByDisponiveisInstalacao();
    }

    public List<Equipamento> buscarDisponiveisParaWifi() {
        return equipamentoRepository.findByDisponiveisWifi();
    }

    public Iterable<Equipamento> buscarTodos() {
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
        Equipamento e = equipamentoRepository.findOne(id);
        equipamentoRepository.delete(e);
    }
}
