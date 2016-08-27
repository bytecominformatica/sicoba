package br.com.clairtonluz.sicoba.service.provedor;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 09/04/16.
 */
public interface Servidor extends AutoCloseable {

    <T> T connect(String host, int port, String user, String pass);

    <T> T execute(String command);


}
