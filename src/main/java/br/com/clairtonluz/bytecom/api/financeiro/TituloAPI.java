package br.com.clairtonluz.bytecom.api.financeiro;

import br.com.clairtonluz.bytecom.model.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.bytecom.model.entity.financeiro.Titulo;
import br.com.clairtonluz.bytecom.model.service.financeiro.TituloService;
import br.com.clairtonluz.bytecom.pojo.financeiro.Carne;
import br.com.clairtonluz.bytecom.util.MensagemException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("titulos")
public class TituloAPI {

    @Inject
    private TituloService tituloService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GET
    @Path("ocorrencia")
    public List<Titulo> porDataOcorrencia(@QueryParam("inicio") @NotNull(message = "Início é obrigatório") String inicio,
                                          @QueryParam("fim") @NotNull(message = "Fim é obrigatório") String fim,
                                          @QueryParam("status") StatusTitulo status) throws ParseException {

        Date i = sdf.parse(inicio);
        Date f = sdf.parse(fim);
        return tituloService.buscarPorDataOcorreciaStatus(i, f, status);
    }

    @GET
    @Path("vencimento")
    public List<Titulo> porDataVencimento(@QueryParam("inicio") @NotNull(message = "Início é obrigatório") String inicio,
                                          @QueryParam("fim") @NotNull(message = "Fim é obrigatório") String fim,
                                          @QueryParam("status") StatusTitulo status) throws ParseException {

        Date i = sdf.parse(inicio);
        Date f = sdf.parse(fim);
        return tituloService.buscarPorDataVencimentoStatus(i, f, status);
    }

    @GET
    @Path("vencidos")
    public List<Titulo> getVencidos() {
        return tituloService.buscarVencidos();
    }

    @GET
    @Path("/cliente/{clienteId}/nova")
    public Titulo getNova(@PathParam("clienteId") Integer clienteId) {
        return tituloService.getNovo(clienteId);
    }

    @GET
    @Path("cliente/{clienteId}")
    public List<Titulo> getPorCliente(@PathParam("clienteId") Integer clienteId) {
        return tituloService.buscarPorCliente(clienteId);
    }

    @GET
    @Path("{id}")
    public Titulo get(@PathParam("id") Integer id) {
        return tituloService.buscarPorId(id);
    }

    @POST
    @Path("/carne")
    public List<Titulo> criarCarne(Carne carne) throws MensagemException {
        return tituloService.criarCarne(carne);
    }

    @POST
    public Titulo save(Titulo titulo) throws MensagemException {
        return tituloService.save(titulo);
    }

    @POST
    @Path("/{id}")
    public Titulo update(Titulo titulo) throws MensagemException {
        return tituloService.save(titulo);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") Integer id) {
        tituloService.remove(id);
    }

}
