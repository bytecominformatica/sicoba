package br.com.clairtonluz.bytecom.api;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
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

    @GET
    @Path("/sem_mensalidade")
    public List<Cliente> getSemMensalidade() {
        return clienteService.buscarSemMensalidade();
    }

    @GET
    public List<Cliente> getPorStatus(@QueryParam("status") StatusCliente status) {
        return clienteService.buscarPorStatus(status);
    }

}
