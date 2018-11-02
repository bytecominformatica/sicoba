package br.com.clairtonluz.sicoba.api.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NFe;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItem;
import br.com.clairtonluz.sicoba.service.financeiro.nf.syncnfe.NFeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/notas")
public class NotaAPI {

    private final NFeService nfeService;

    @Autowired
    public NotaAPI(NFeService nfeService) {
        this.nfeService = nfeService;
    }

    @PostMapping("gerar")
    public List<NFe> gerar(@RequestBody List<Charge> charges) {
        return nfeService.generateNotas(charges);
    }

    @RequestMapping(value = "itens/dateprovision", method = RequestMethod.GET)
    public List<NfeItem> findItensByDatePrestacao(
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam(value = "gerencianetAccount", required = false) Integer gerencianetAccountId) {
        return nfeService.findItensByDatePrestacao(LocalDate.parse(start), LocalDate.parse(end));
    }

    @RequestMapping(value = "syncnfe/listar", method = RequestMethod.GET)
    public List<NFe> getNotasGeradas() {
        return nfeService.getAll();
    }

    @PostMapping("syncnfe/files")
    public void syncnfeFiles(@RequestBody List<NfeItem> nfeItemList, HttpServletResponse response) throws IOException {
        nfeService.generateFiles(nfeItemList, response);
    }

    @DeleteMapping("all")
    public void deleteAll(@RequestParam("id") List<Integer> ids) {
        nfeService.deleteAll(ids);
    }

    @RequestMapping(value = "syncnfe/busca", method = RequestMethod.GET)
    public List<NFe> buscaNfe() {
        return nfeService.busca();
    }
}
