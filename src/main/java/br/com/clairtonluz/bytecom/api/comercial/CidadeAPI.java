package br.com.clairtonluz.bytecom.api.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cidade;
import br.com.clairtonluz.bytecom.model.service.comercial.CidadeService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by clairtonluz on 31/12/15.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("cidades")
public class CidadeAPI {

    @Inject
    private CidadeService cidadeService;

    @GET
    public List<Cidade> query() {
        return cidadeService.buscarTodos();
    }

}
