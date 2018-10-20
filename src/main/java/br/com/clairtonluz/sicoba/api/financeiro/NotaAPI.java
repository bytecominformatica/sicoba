package br.com.clairtonluz.sicoba.api.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NFe;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.SyncNFeImportacao;
import br.com.clairtonluz.sicoba.service.financeiro.nf.syncnfe.SyncNFeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/notas")
public class NotaAPI {

    private final SyncNFeService syncNFeService;

    @Autowired
    public NotaAPI(SyncNFeService syncNFeService) {
        this.syncNFeService = syncNFeService;
    }

    @PostMapping("gerar")
    public List<NFe> gerar(@RequestBody List<Charge> charges) {
        return syncNFeService.generateNotas(charges);
    }

    @GetMapping
    public List<NFe> query() {
        return syncNFeService.busca();
    }

    @RequestMapping(value = "syncnfe/listar", method = RequestMethod.GET)
    public List<NFe> getNotasGeradas() {
        return syncNFeService.getAll();
    }

    @PostMapping("syncnfe/files")
    public SyncNFeImportacao syncnfeFiles(@RequestBody List<NFe> notas) {
        return syncNFeService.generateFiles(notas);
    }

    @RequestMapping(value = "syncnfe/busca", method = RequestMethod.GET)
    public List<NFe> buscaNfe() {
        return syncNFeService.busca();
    }
}
