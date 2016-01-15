package br.com.clairtonluz.bytecom.api.provedor;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.bytecom.model.service.provedor.MikrotikService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by clairtonluz on 09/01/16.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("mikrotiks")
public class MikrotikAPI {

    @Inject
    private MikrotikService mikrotikService;

    @GET
    public List<Mikrotik> query() {
        return mikrotikService.buscarTodos();
    }

    @GET
    @Path("/{id}")
    public Mikrotik getPorId(@PathParam("id") Integer id) {
        return mikrotikService.buscarPorId(id);
    }

    @POST
    public Mikrotik save(@Valid Mikrotik mikrotik) {
        return mikrotikService.save(mikrotik);
    }

    @POST
    @Path("/{id}")
    public Mikrotik update(@Valid Mikrotik mikrotik) {
        return mikrotikService.save(mikrotik);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") @NotNull Integer id) {
        mikrotikService.remove(id);
    }
}
