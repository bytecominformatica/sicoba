package br.com.clairtonluz.sicoba.api.financeiro.gerencianet;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/gerencianet")
public class NotificationAPI {

    @RequestMapping(value = "notification", method = RequestMethod.POST)
    public void gerar(@RequestBody JSONObject body) {
        System.out.println(body);
    }

}
