package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.util.StringUtil;
import org.json.JSONObject;

/**
 * Created by clairton on 09/11/16.
 */
public class Credentials {

    private static Credentials instance;

    private final String clientId;
    private final String clientSecret;
    private final String notificationUrl;
    private final boolean sandbox;
    private final JSONObject options;


    public static synchronized Credentials getInstance() {
        if (instance == null) {
            instance = new Credentials();
        }
        return instance;
    }

    private Credentials() {
        this.clientId = System.getenv("CLIENT_ID");
        this.clientSecret = System.getenv("CLIENT_SECRET");
        String notificationUrl = System.getenv("NOTIFICATION_URL");
        this.notificationUrl = StringUtil.isEmpty(notificationUrl) ? null : notificationUrl;
        this.sandbox = Boolean.parseBoolean(System.getenv("SANDBOX"));

        options = new JSONObject();
        options.put("client_id", getClientId());
        options.put("client_secret", getClientSecret());
        options.put("sandbox", isSandbox());
    }

    public JSONObject getOptions() {
        return options;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }
}
