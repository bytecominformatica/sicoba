package br.com.clairtonluz.sicoba.api.comercial;

import br.com.clairtonluz.sicoba.api.CrudEndpoint;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.sicoba.service.comercial.conexao.ConexaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/conexoes")
public class ConexaoAPI extends CrudEndpoint<Conexao, ConexaoRepository,ConexaoService, Integer> {

    public ConexaoAPI(ConexaoService service) {
        super(service);
    }

    @RequestMapping(value = "/atualizarTodos", method = RequestMethod.GET)
    public void getAtualizarTodos() throws Exception {
        service.atualizarTodos();
    }

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
    public Conexao getPorCliente(@PathVariable Integer id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return service.buscarOptionalPorCliente(cliente);
    }

    @RequestMapping(value = "/ip/livre", method = RequestMethod.GET)
    public Map<String, String> getIpLivre() {
        String ip = service.buscarIpLivre();
        Map<String, String> map = new HashMap<>();
        map.put("ip", ip);
        return map;
    }

}
