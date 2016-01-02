package br.com.clairtonluz.bytecom.api.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("clientes")
public class ClienteAPI {

    @Inject
    private ClienteService clienteService;

    @GET
    @Path("/{id}")
    public Cliente getPorId(@PathParam("id") Integer id) {
        return clienteService.buscarPorId(id);
    }


    @POST
    public Cliente save(Cliente cliente) throws Exception {
        return clienteService.save(cliente);
    }

    @POST
    @Path("/{id}")
    public Cliente update(Cliente cliente) throws Exception {
        return clienteService.save(cliente);
    }

    @GET
    @Path("/{id}/conexao")
    public Conexao getConexao(@PathParam("id") Integer clienteId) {
        return clienteService.buscarPorCliente(clienteId);
    }

    @GET
    @Path("/sem_mensalidade")
    public List<Cliente> getSemMensalidade() {
        return clienteService.buscarSemMensalidade();
    }

    @GET
    @Path("/ultimos_alterados")
    public List<Cliente> getUltimosAlterados() {
        return clienteService.buscarUltimosAlterados();
    }

    @GET
    public List<Cliente> query(@QueryParam("nome") String nome,
                               @QueryParam("status") StatusCliente status) {

        return clienteService.query(nome, status);
    }

}
