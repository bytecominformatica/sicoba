package br.com.clairtonluz.sicoba.api.estoque;

import br.com.clairtonluz.sicoba.model.entity.estoque.Equipamento;
import br.com.clairtonluz.sicoba.service.estoque.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by clairtonluz on 03/01/16.
 */
@RestController
@RequestMapping("api/equipamentos")
public class EquipamentoAPI {

    @Autowired
    private EquipamentoService equipamentoService;

    @RequestMapping(value = "instalacao/disponiveis", method = RequestMethod.GET)
    public List<Equipamento> buscarDisponiveisParaInstalacao() {
        return equipamentoService.buscarDisponiveisParaInstalacao();
    }

    @RequestMapping(value = "wifi/disponiveis", method = RequestMethod.GET)
    public List<Equipamento> buscarDisponiveisParaWifi() {
        return equipamentoService.buscarDisponiveisParaWifi();
    }


    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Equipamento> query() {
        return equipamentoService.buscarTodos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Equipamento getPorId(@PathVariable Integer id) {
        return equipamentoService.buscarPorId(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Equipamento save(@Valid @RequestBody Equipamento equipamento) {
        return equipamentoService.save(equipamento);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Equipamento update(@Valid @RequestBody Equipamento equipamento) {
        return equipamentoService.save(equipamento);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable @NotNull Integer id) {
        equipamentoService.remove(id);
    }
}
