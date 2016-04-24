package br.com.clairtonluz.sicoba.api.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.service.comercial.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by clairtonluz on 14/11/15.
 */
@RestController
@RequestMapping("api/contratos")
public class ContratoAPI {

    @Autowired
    private ContratoService contratoService;

    @RequestMapping(value = "novos", method = RequestMethod.GET)
    public List<Contrato> getNovos() {
        return contratoService.buscarRecentes();
    }

    @RequestMapping(value = "cliente/{clienteId}", method = RequestMethod.GET)
    public Contrato buscarPorCliente(@PathVariable Integer clienteId) {
        return contratoService.buscarPorCliente(clienteId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Contrato save(@Valid @RequestBody Contrato contrato) {
        return contratoService.save(contrato);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Contrato update(@Valid @RequestBody Contrato contrato) {
        return contratoService.save(contrato);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Integer id) {
        contratoService.remove(id);
    }


    @RequestMapping(value = "equipamento/{id}", method = RequestMethod.GET)
    public Contrato buscarPorEquipamento(@PathVariable Integer id) {
        return contratoService.buscarPorEquipamento(id);
    }
}