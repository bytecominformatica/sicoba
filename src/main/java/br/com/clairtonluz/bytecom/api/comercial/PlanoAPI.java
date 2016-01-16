package br.com.clairtonluz.bytecom.api.comercial;

import br.com.clairtonluz.bytecom.model.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.service.comercial.PlanoService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by clairtonluz on 11/01/16.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("planos")
public class PlanoAPI {

    @Inject
    private PlanoService planoService;

    @GET
    public List<Plano> query() {
        return planoService.buscarTodos();
    }

    @GET
    @Path("/{id}")
    public Plano getPorId(@PathParam("id") Integer id) {
        return planoService.buscarPorId(id);
    }


    @POST
    public Plano save(@Valid Plano plano) {
        return planoService.save(plano);
    }

    @POST
    @Path("/{id}")
    public Plano update(@Valid Plano plano) {
        return planoService.save(plano);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") @NotNull Integer id) {
        planoService.remover(id);
    }
}
