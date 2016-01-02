package br.com.clairtonluz.bytecom.api.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.service.comercial.PlanoService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by clairtonluz on 01/01/16.
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

}
