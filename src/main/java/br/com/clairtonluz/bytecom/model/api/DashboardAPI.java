package br.com.clairtonluz.bytecom.model.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by clairtonluz on 14/11/15.
 */
@Produces("application/json")
@Consumes("application/json")
@Path("/dashboard")
public class DashboardAPI {

    @GET
    @Path("instalacoes")
    public Integer getQuantidadeInstalacoesDoMes(){
        return 1;
    }

}
