package br.com.clairtonluz.sicoba.api.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "notification", method = RequestMethod.POST)
    public void gerar(@RequestBody String body) {
        String token = body.substring(body.indexOf('=') + 1);
        notificationService.processNotification(token);
    }

}
