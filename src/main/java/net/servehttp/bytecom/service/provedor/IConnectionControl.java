package net.servehttp.bytecom.service.provedor;

import net.servehttp.bytecom.model.jpa.entity.provedor.IConnectionClienteCertified;
import net.servehttp.bytecom.model.jpa.entity.provedor.IServer;

import java.io.Serializable;


public interface IConnectionControl extends Serializable {

    void save(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void remove(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void kickout(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    MikrotikConnection setAutoCloseable(boolean autoCloseable) throws Exception;

}
