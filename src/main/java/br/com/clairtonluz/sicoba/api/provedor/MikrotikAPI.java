package br.com.clairtonluz.sicoba.api.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.sicoba.service.provedor.MikrotikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

/**
 * Created by clairtonluz on 09/01/16.
 */
@RestController
@RequestMapping("api/mikrotiks")
public class MikrotikAPI {

    @Autowired
    private MikrotikService mikrotikService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Mikrotik> query() {
        return mikrotikService.buscarTodos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Mikrotik getPorId(@PathVariable Integer id) {
        return mikrotikService.buscarPorId(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Mikrotik save(@Valid @RequestBody Mikrotik mikrotik) {
        return mikrotikService.save(mikrotik);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Mikrotik update(@Valid @RequestBody Mikrotik mikrotik) {
        return mikrotikService.save(mikrotik);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable @NotNull Integer id) {
        mikrotikService.remove(id);
    }
}
