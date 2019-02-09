package br.com.clairtonluz.sicoba.api.financeiro;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno.Header;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.RetornoPojo;
import br.com.clairtonluz.sicoba.service.financeiro.RetornoCaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by clairtonluz on 11/01/16.
 */
@RestController
@RequestMapping("api/retornos")
public class RetornoAPI {

    @Autowired
    private RetornoCaixaService retornoCaixaService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<RetornoPojo> upload(@RequestParam("file") MultipartFile file) throws Exception {
        List<RetornoPojo> retornoPojos;
        if (!file.isEmpty()) {
            Header header = retornoCaixaService.parse(file.getInputStream(), file.getName());
            retornoPojos = retornoCaixaService.processarHeader(header);
        } else {
            throw new BadRequestException("Arquivo vazio");
        }
        return retornoPojos;
    }
}
