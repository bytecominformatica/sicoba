package br.com.clairtonluz.bytecom.api.provedor;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.bytecom.model.service.provedor.MikrotikService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
