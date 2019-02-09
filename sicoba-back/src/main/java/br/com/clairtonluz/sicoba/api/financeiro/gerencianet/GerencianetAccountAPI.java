package br.com.clairtonluz.sicoba.api.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GerencianetAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/gerencianet/accounts")
public class GerencianetAccountAPI {

    @Autowired
    private GerencianetAccountService gerencianetAccountService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<GerencianetAccount> query() {
        return gerencianetAccountService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GerencianetAccount findById(@PathVariable Integer id) {
        return gerencianetAccountService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public GerencianetAccount criar(@Valid @RequestBody GerencianetAccount gerencianetAccount) {
        return gerencianetAccountService.save(gerencianetAccount);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public GerencianetAccount update(@Valid @RequestBody GerencianetAccount gerencianetAccount) {
        return gerencianetAccountService.save(gerencianetAccount);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable @NotNull Integer id) {
        gerencianetAccountService.remover(id);
    }


}
