package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.notification;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clairton on 09/11/16.
 */
@Service
class NotificationGNService {

    private static final String GET_NOTIFICATION = "getNotification";
    private final GNService gnService;

    @Autowired
    NotificationGNService(GNService gnService) {
        this.gnService = gnService;
    }

    JSONObject getNotification(GerencianetAccount account, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);

        return gnService.call(account, GET_NOTIFICATION, params);
    }

}
