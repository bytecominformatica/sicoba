package br.com.clairtonluz.bytecom.api.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    public List<Contrato> getNovos() {
        return contratoService.buscarRecentes();
    }

    @GET
    @Path("cliente/{clienteId}")
    public Contrato buscarPorCliente(@PathParam("clienteId") Integer clienteId) {
        return contratoService.buscarPorCliente(clienteId);
    }

    @POST
    public Contrato save(Contrato contrato) throws Exception {
        return contratoService.save(contrato);
    }

    @POST
    @Path("/{id}")
    public Contrato update(Contrato contrato) throws Exception {
        return contratoService.save(contrato);
    }

}
