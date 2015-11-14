package br.com.clairtonluz.bytecom.model.api;

import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by clairtonluz on 14/11/15.
 */
@Produces("application/json")
@Consumes("application/json")
@Path("contract")
public class ContractAPI {

    @Inject
    private ContratoService contratoService;

    @GET
    @Path("installed/this/month/amount")
    public Integer getInstalledThisMonth() {
        return contratoService.buscarTodosInstaladoEsseMes().size();
    }

}
