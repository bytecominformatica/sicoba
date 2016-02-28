package br.com.clairtonluz.sicoba.api.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.sicoba.service.provedor.MikrotikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Mikrotik getPorId(@PathParam("id") Integer id) {
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
    public void remove(@PathParam("id") @NotNull Integer id) {
        mikrotikService.remove(id);
    }
}
