package br.com.clairtonluz.sicoba.api.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NFe;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItem;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.SyncNFeImportacao;
import br.com.clairtonluz.sicoba.service.financeiro.nf.syncnfe.NFeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/notas")
public class NotaAPI {

    private final NFeService NFeService;

    @Autowired
    public NotaAPI(NFeService NFeService) {
        this.NFeService = NFeService;
    }

    @PostMapping("gerar")
    public List<NFe> gerar(@RequestBody List<Charge> charges) {
        return NFeService.generateNotas(charges);
    }

    @PostMapping("gerar/syncnfe")
    public List<NFe> gerarSyncNfe(@RequestBody List<NFe> notas, ServletServerHttpResponse response) {
        System.out.println("Notas: " + notas.size());
        return notas;
    }

    @RequestMapping(value = "itens/dateprovision", method = RequestMethod.GET)
    public List<NfeItem> findItensByDatePrestacao(
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam(value = "gerencianetAccount", required = false) Integer gerencianetAccountId) {
        return NFeService.findItensByDatePrestacao(LocalDate.parse(start), LocalDate.parse(end));
    }

    @RequestMapping(value = "syncnfe/listar", method = RequestMethod.GET)
    public List<NFe> getNotasGeradas() {
        return NFeService.getAll();
    }

    @PostMapping("syncnfe/files")
    public SyncNFeImportacao syncnfeFiles(@RequestBody List<NFe> notas) {
        return NFeService.generateFiles(notas);
    }

    @RequestMapping(value = "syncnfe/busca", method = RequestMethod.GET)
    public List<NFe> buscaNfe() {
        return NFeService.busca();
    }
}
