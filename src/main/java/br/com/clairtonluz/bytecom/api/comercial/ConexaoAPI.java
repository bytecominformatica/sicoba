package br.com.clairtonluz.bytecom.api.comercial;

import br.com.clairtonluz.bytecom.model.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by clairtonluz on 19/12/15.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("conexoes")
public class ConexaoAPI {

    @Inject
    private ConexaoService conexaoService;

    @GET
    @Path("/cliente/{id}")
    public Conexao getPorCliente(@PathParam("id") Integer id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return conexaoService.buscarOptionalPorCliente(cliente);
    }

    @GET
    @Path("/ip/{ip}")
    public Conexao getPorIp(@PathParam("ip") String ip) {
        return conexaoService.buscarPorIp(ip);
    }

    @GET
    @Path("/ip/livre")
    public Map<String, String> getIpLivre() {
        String ip = conexaoService.buscarIpLivre();
        Map<String, String> map = new HashMap<>();
        map.put("ip", ip);
        return map;
    }

    @POST
    public Conexao save(Conexao conexao) throws Exception {
        return conexaoService.save(conexao);
    }

    @POST
    @Path("/{id}")
    public Conexao update(Conexao conexao) throws Exception {
        return conexaoService.save(conexao);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") Integer id) {
        conexaoService.remove(id);
    }


}
