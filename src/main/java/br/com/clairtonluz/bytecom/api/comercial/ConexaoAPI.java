package br.com.clairtonluz.bytecom.api.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
        return conexaoService.buscarPorCliente(cliente);
    }

    @GET
    @Path("/ip/{ip}")
    public Conexao getPorIp(@PathParam("ip") String ip) {
        return conexaoService.buscarPorIp(ip);
    }


}
