package br.com.clairtonluz.bytecom.api;

import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by clairtonluz on 14/11/15.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("contratos")
public class ContratoAPI {

    @Inject
    private ContratoService contratoService;

    @GET
    @Path("novos")
    public Integer getInstalledThisMonth() {
        return contratoService.buscarTodosInstaladoEsseMes().size();
    }

}
