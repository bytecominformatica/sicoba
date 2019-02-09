package br.com.clairtonluz.sicoba.model.entity.provedor;

public interface IConnectionClienteCertified extends IConnectionClient {

    String getLogin();

    String getPass();

    boolean isDisabled();
}
