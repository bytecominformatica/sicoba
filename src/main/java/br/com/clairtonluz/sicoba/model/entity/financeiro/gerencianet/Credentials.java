package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet;

import org.json.JSONObject;

/**
 * Created by clairton on 09/11/16.
 */
public class Credentials {

    private String clientId;
    private String clientSecret;
    private boolean sandbox;
    private JSONObject options;

    public Credentials() {
        this.clientId = System.getenv("CLIENT_ID");
        this.clientSecret = System.getenv("CLIENT_SECRET");
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
}
