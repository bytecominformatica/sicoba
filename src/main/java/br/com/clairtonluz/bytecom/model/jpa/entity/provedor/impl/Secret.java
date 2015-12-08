package br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.IConnectionClienteCertified;

/**
 * Created by clairtonluz on 25/11/15.
 */
public class Secret implements IConnectionClienteCertified {

    private String login;
    private String pass;
    private String ip;
    private String mac;
    private String profile;

    public Secret(String login, String pass, String ip, String profile) {
        this.login = login;
        this.pass = pass;
        this.ip = ip;
        this.profile = profile;
    }

    @Override
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
