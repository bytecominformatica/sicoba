package br.com.clairtonluz.bytecom.api.estoque;

import br.com.clairtonluz.bytecom.model.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.service.estoque.EquipamentoService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by clairtonluz on 03/01/16.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("equipamentos")
public class EquipamentoAPI {

    @Inject
    private EquipamentoService equipamentoService;

    @GET
    @Path("instalacao/disponiveis")
    public List<Equipamento> buscarDisponiveisParaInstalacao() {
        return equipamentoService.buscarDisponiveisParaInstalacao();
    }

    @GET
    @Path("wifi/disponiveis")
    public List<Equipamento> buscarDisponiveisParaWifi() {
        return equipamentoService.buscarDisponiveisParaWifi();
    }


    @GET
    public List<Equipamento> query() {
        return equipamentoService.buscarTodos();
    }

    @GET
    @Path("/{id}")
    public Equipamento getPorId(@PathParam("id") Integer id) {
        return equipamentoService.buscarPorId(id);
    }

    @POST
    public Equipamento save(@Valid Equipamento equipamento) {
        return equipamentoService.save(equipamento);
    }

    @POST
    @Path("/{id}")
    public Equipamento update(@Valid Equipamento equipamento) {
        return equipamentoService.save(equipamento);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") @NotNull Integer id) {
        equipamentoService.remove(id);
    }
}
