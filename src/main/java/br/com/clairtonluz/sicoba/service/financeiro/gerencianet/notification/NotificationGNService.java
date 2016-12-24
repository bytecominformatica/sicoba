package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.notification;

import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clairton on 09/11/16.
 */
@Service
class NotificationGNService {

    private static final String GET_NOTIFICATION = "getNotification";

    JSONObject getNotification(String token) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);

        return GNService.call(GET_NOTIFICATION, params);
    }

}
