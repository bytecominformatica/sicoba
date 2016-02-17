package br.com.clairtonluz.sicoba.service.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.IConnectionClienteCertified;
import br.com.clairtonluz.sicoba.model.entity.provedor.IServer;

import java.io.Serializable;


public interface IConnectionControl extends Serializable {

    void save(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void remove(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void kickout(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    MikrotikConnection setAutoCloseable(boolean autoCloseable) throws Exception;

}
