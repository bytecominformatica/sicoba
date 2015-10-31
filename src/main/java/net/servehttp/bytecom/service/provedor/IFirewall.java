package net.servehttp.bytecom.service.provedor;

import net.servehttp.bytecom.model.jpa.entity.provedor.IConnectionClienteCertified;
import net.servehttp.bytecom.model.jpa.entity.provedor.IServer;

public interface IFirewall {
    void lock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void unlock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
}
