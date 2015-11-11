package br.com.clairtonluz.bytecom.service.provedor;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.IServer;
import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.IConnectionClienteCertified;

import java.io.Serializable;


public interface IConnectionControl extends Serializable {

    void save(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void remove(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void kickout(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    MikrotikConnection setAutoCloseable(boolean autoCloseable) throws Exception;

}
