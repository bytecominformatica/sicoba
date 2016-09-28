package br.com.clairtonluz.sicoba.api.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.Cedente;
import br.com.clairtonluz.sicoba.service.financeiro.CedenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by clairtonluz on 19/09/16.
 */
@RestController()
@RequestMapping("api/cedentes")
public class CedenteAPI {

    @Autowired
    private CedenteService cedenteService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Cedente> query() {
        return cedenteService.buscarTodos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Cedente getPorId(@PathVariable Integer id) {
        return cedenteService.buscarPorId(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public Cedente save(@Valid @RequestBody Cedente cedente) {
        return cedenteService.save(cedente);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Cedente update(@Valid @RequestBody Cedente cedente) {
        return cedenteService.save(cedente);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable @NotNull Integer id) {
        cedenteService.remover(id);
    }
}
