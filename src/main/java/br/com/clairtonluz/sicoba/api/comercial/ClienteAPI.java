package br.com.clairtonluz.sicoba.api.comercial;

import br.com.clairtonluz.sicoba.api.CrudEndpoint;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.service.comercial.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/clientes")
public class ClienteAPI extends CrudEndpoint<Cliente, ClienteRepository, ClienteService, Integer> {

    @Autowired
    public ClienteAPI(ClienteService service) {
        super(service);
    }

    @RequestMapping(value = "/{id}/conexao", method = RequestMethod.GET)
    public Conexao getConexao(@PathVariable Integer id) {
        return service.buscarConexaoPorCliente(id);
    }

    @RequestMapping(value = "/sem_titulo", method = RequestMethod.GET)
    public List<Cliente> getSemTitulo() {
        return service.buscarSemTitulo();
    }

    @RequestMapping(value = "/ultimos_alterados", method = RequestMethod.GET)
    public List<Cliente> getUltimosAlterados() {
        return service.buscarUltimosAlterados();
    }

    @RequestMapping(value = "/ultimos_cancelados", method = RequestMethod.GET)
    public List<Cliente> getUltimosCancelados() {
        return service.buscarUltimosCancelados();
    }
}
