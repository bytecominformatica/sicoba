package br.com.clairtonluz.bytecom.api.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.estoque.Equipamento;
import br.com.clairtonluz.bytecom.model.service.estoque.EquipamentoService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
