package br.com.clairtonluz.sicoba.api.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/gerencianet")
public class NotificationAPI {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/{accountId}/notification", method = RequestMethod.POST)
    public void process(@PathVariable Integer accountId, @RequestBody String body) {
        String token = body.substring(body.indexOf('=') + 1);
        notificationService.processNotification(accountId, token);
    }

    @RequestMapping(value = "/{accountId}/lote/notification", method = RequestMethod.POST)
    public Map process(@PathVariable Integer accountId, @RequestBody List<String> tokenRecebidos) {
        Map map = new HashMap<String, String>();
        Set<String> tokens = new HashSet<>(tokenRecebidos);
        int count = 0;
        List<String> falhas = new ArrayList<>();
        for (String token : tokens) {
            try {
                notificationService.processNotification(accountId, token);
                count++;
            } catch (Exception e) {
                falhas.add(token + ":" + e.getLocalizedMessage());
            }
        }
        map.put("recebidos", String.valueOf(tokenRecebidos.size()));
        map.put("distintos", String.valueOf(tokens.size()));
        map.put("processados", String.valueOf(count));
        map.put("falhas", falhas.toString());

        return map;
    }

}
