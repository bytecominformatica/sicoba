package br.com.clairtonluz.sicoba.api;

import br.com.clairtonluz.sicoba.util.SendEmail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("api/testes")
public class TesteAPI {

    @RequestMapping(value = "notificar_admin", method = RequestMethod.GET)
    public void porDataOcorrencia() throws ParseException {
        SendEmail.sendToAdmin("TESTE", "TESTE");
    }
}
