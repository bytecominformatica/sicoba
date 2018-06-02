package br.com.clairtonluz.sicoba.api.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.sicoba.service.provedor.MikrotikService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by clairtonluz on 09/01/16.
 */
@RestController
@RequestMapping("api/mikrotiks")
public class MikrotikAPI {

    private final MikrotikService mikrotikService;

    public MikrotikAPI(MikrotikService mikrotikService) {
        this.mikrotikService = mikrotikService;
    }

    @GetMapping
    public Iterable<Mikrotik> query() {
        return mikrotikService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Mikrotik getPorId(@PathVariable Integer id) {
        return mikrotikService.buscarPorId(id);
    }

    @PostMapping
    public Mikrotik save(@Valid @RequestBody Mikrotik mikrotik) {
        return mikrotikService.save(mikrotik);
    }

    @PostMapping("/{id}")
    public Mikrotik update(@Valid @RequestBody Mikrotik mikrotik) {
        return mikrotikService.save(mikrotik);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable @NotNull Integer id) {
        mikrotikService.remove(id);
    }

    @PostMapping("/{id}/host")
    public void atualizarHost(@PathVariable @NotNull Integer id,
                              @RequestParam("token") String token,
                              @RequestBody Mikrotik mikrotik) {
        mikrotikService.atualizarHost(id, token, mikrotik);
    }
}
