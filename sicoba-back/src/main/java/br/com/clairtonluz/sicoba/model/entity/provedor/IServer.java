package br.com.clairtonluz.sicoba.model.entity.provedor;

import java.io.Serializable;

public interface IServer extends Serializable {

    String getHost();

    String getLogin();

    String getPass();

    int getPort();

    String getName();

    String getDescription();
}
