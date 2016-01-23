package br.com.clairtonluz.sicoba.api.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import br.com.clairtonluz.sicoba.model.service.comercial.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by clairtonluz on 11/01/16.
 */
@RestController()
@RequestMapping("api/planos")
public class PlanoAPI {

    @Autowired
    private PlanoService planoService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Plano> query() {
        return planoService.buscarTodos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Plano getPorId(@PathVariable Integer id) {
        return planoService.buscarPorId(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public Plano save(@Valid Plano plano) {
        return planoService.save(plano);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Plano update(@Valid Plano plano) {
        return planoService.save(plano);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public void remove(@PathVariable @NotNull Integer id) {
        planoService.remover(id);
    }
}
