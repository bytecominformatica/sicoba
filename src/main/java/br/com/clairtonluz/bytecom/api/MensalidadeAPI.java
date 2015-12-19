package br.com.clairtonluz.bytecom.api;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.service.financeiro.MensalidadeService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by clairtonluz on 14/11/15.
 */
@Produces("application/json")
@Consumes("application/json")
@Path("mensalidades")
public class MensalidadeAPI {

    @Inject
    private MensalidadeService mensalidadeService;

    @GET
    @Path("atrasada")
    public List<Mensalidade> getAtrasadas() {
        return mensalidadeService.buscarMensalidadesAtrasada();
    }

}
