package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.util.StringUtil;

import javax.persistence.*;

/**
 * Created by clairton on 21/12/16.
 */
@Entity
@Table(name = "gerencianet_account")
public class GerencianetAccount extends BaseEntity {
    private String name;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_secret")
    private String clientSecret;
    private Double fine;
    private Double interest;
    private boolean sandbox;
    @Column(name = "notify_client")
    private boolean notifyClient;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public boolean isNotifyClient() {
        return notifyClient;
    }

    public void setNotifyClient(boolean notifyClient) {
        this.notifyClient = notifyClient;
    }


}
