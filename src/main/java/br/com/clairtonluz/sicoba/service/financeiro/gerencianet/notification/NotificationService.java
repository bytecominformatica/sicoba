package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.notification;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.Credentials;
import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clairton on 20/12/16.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationGNService notificationGNService;
    public void getNotification(String token) {
        JSONObject notification = notificationGNService.getNotification(token);
    }
}
