package br.com.clairtonluz.sicoba.api.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.service.comercial.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/clientes")
public class ClienteAPI {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Cliente getPorId(@PathVariable Integer id) {
        return clienteService.buscarPorId(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public Cliente save(@Valid @RequestBody Cliente cliente) throws Exception {
        return clienteService.save(cliente);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Cliente update(@Valid @RequestBody Cliente cliente) throws Exception {
        return clienteService.save(cliente);
    }

    @RequestMapping(value = "/{id}/conexao", method = RequestMethod.GET)
    public Conexao getConexao(@PathVariable Integer id) {
        return clienteService.buscarPorCliente(id);
    }

    @RequestMapping(value = "/sem_titulo", method = RequestMethod.GET)
    public List<Cliente> getSemTitulo() {
        return clienteService.buscarSemTitulo();
    }

    @RequestMapping(value = "/ultimos_alterados", method = RequestMethod.GET)
    public List<Cliente> getUltimosAlterados() {
        return clienteService.buscarUltimosAlterados();
    }


    @RequestMapping(value = "/ultimos_cancelados", method = RequestMethod.GET)
    public List<Cliente> getUltimosCancelados() {
        return clienteService.buscarUltimosCancelados();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Cliente> query(@RequestParam(value = "nome", required = false) String nome,
                               @RequestParam(value = "status", required = false) StatusCliente status) {

        return clienteService.query(nome, status);
    }

}
