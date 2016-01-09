package br.com.clairtonluz.bytecom.api;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.service.financeiro.MensalidadeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("mensalidades")
public class MensalidadeAPI {

    @Inject
    private MensalidadeService mensalidadeService;

    @GET
    @Path("atrasada")
    public List<Mensalidade> getAtrasadas() {
        return mensalidadeService.buscarMensalidadesAtrasada();
    }

    @GET
    @Path("/cliente/{clienteId}/nova")
    public Mensalidade getNova(@PathParam("clienteId") Integer clienteId) {
        return mensalidadeService.getNova(clienteId);
    }

    @GET
    @Path("cliente/{clienteId}")
    public List<Mensalidade> getPorCliente(@PathParam("clienteId") Integer clienteId) {
        return mensalidadeService.buscarPorCliente(clienteId);
    }

    @GET
    @Path("{id}")
    public Mensalidade get(@PathParam("id") Integer id) {
        return mensalidadeService.buscarPorId(id);
    }

    @POST
    public Mensalidade save(Mensalidade mensalidade) {
        return mensalidadeService.save(mensalidade);
    }

    @POST
    @Path("/{id}")
    public Mensalidade update(Mensalidade mensalidade) {
        return mensalidadeService.save(mensalidade);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") Integer id) {
        mensalidadeService.remove(id);
    }

}
