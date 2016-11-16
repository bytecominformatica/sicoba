package br.com.clairtonluz.sicoba.api.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.Carne;
import br.com.clairtonluz.sicoba.service.financeiro.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/titulos")
public class TituloAPI {

    @Autowired
    private TituloService tituloService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value = "ocorrencia", method = RequestMethod.GET)
    public List<Titulo> porDataOcorrencia(@RequestParam("inicio") String inicio,
                                          @RequestParam("fim") String fim,
                                          @RequestParam(value = "status", required = false) StatusTitulo status) throws ParseException {

        Date i = sdf.parse(inicio);
        Date f = sdf.parse(fim);
        return tituloService.buscarPorDataOcorreciaStatus(i, f, status);
    }

    @RequestMapping(value = "vencimento", method = RequestMethod.GET)
    public List<Titulo> porDataVencimento(@RequestParam("inicio") @NotNull(message = "Início é obrigatório") String inicio,
                                          @RequestParam("fim") @NotNull(message = "Fim é obrigatório") String fim,
                                          @RequestParam(value = "status", required = false) StatusTitulo status) throws ParseException {

        Date i = sdf.parse(inicio);
        Date f = sdf.parse(fim);
        return tituloService.buscarPorDataVencimentoStatus(i, f, status);
    }

    @RequestMapping(value = "vencidos", method = RequestMethod.GET)
    public List<Titulo> getVencidos() {
        return tituloService.buscarVencidos();
    }

    @RequestMapping(value = "/cliente/{clienteId}/new", method = RequestMethod.GET)
    public Titulo getNovo(@PathVariable Integer clienteId) {
        return tituloService.getNovo(clienteId);
    }

    @RequestMapping(value = "cliente/{clienteId}", method = RequestMethod.GET)
    public List<Titulo> getPorCliente(@PathVariable Integer clienteId) {
        return tituloService.buscarPorCliente(clienteId);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Titulo get(@PathVariable Integer id) {
        return tituloService.buscarPorId(id);
    }

    @RequestMapping(value = "/carne", method = RequestMethod.POST)
    public List<Titulo> criarCarne(@RequestBody Carne carne) {
        return tituloService.criarCarne(carne);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Titulo save(@Valid @RequestBody Titulo titulo) {
        return tituloService.save(titulo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Titulo update(@Valid @RequestBody Titulo titulo) {
        return tituloService.save(titulo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Integer id) {
        tituloService.remove(id);
    }

    @RequestMapping(value = "/boletos", method = RequestMethod.GET)
    public byte[] gerarPDF(@RequestParam(name = "titulos") List<Integer> titulos) {
        byte[] contents = null;
        if (titulos != null && !titulos.isEmpty()) {
            contents = tituloService.criarBoletos(titulos);
        }
        return contents;
    }
}
