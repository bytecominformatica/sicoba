package br.com.clairtonluz.bytecom.service.provedor;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.IServer;
import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.IConnectionClienteCertified;

public interface IFirewall {
    void lock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void unlock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
}
