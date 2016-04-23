package br.com.clairtonluz.sicoba.model.entity.provedor.impl;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.model.entity.provedor.IServer;

import javax.persistence.*;

@Entity
@Table(name = "mikrotik")
public class Mikrotik extends BaseEntity implements IServer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mikrotik_id_seq")
    @SequenceGenerator(name = "mikrotik_id_seq", sequenceName = "mikrotik_id_seq")
    private Integer id;
    @Lob
    private String description;
    private String name;
    private String host;
    private int port;
    private String login;
    private String pass;

    public Mikrotik() {
        this.login = "admin";
        this.pass = "";
        this.port = 8728;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
