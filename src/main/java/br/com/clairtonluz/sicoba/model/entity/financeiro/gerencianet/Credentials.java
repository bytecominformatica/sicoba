package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet;

/**
 * Created by clairton on 09/11/16.
 */
public class Credentials {

    private String clientId;
    private String clientSecret;
    private boolean sandbox;

    public Credentials() {
        this.clientId = System.getenv("CLIENT_ID");
        this.clientSecret = System.getenv("CLIENT_SECRET");
        this.sandbox = Boolean.parseBoolean(System.getenv("SANDBOX"));
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
