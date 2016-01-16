package br.com.clairtonluz.bytecom.model.service.provedor;

import br.com.clairtonluz.bytecom.model.entity.provedor.IServer;
import br.com.clairtonluz.bytecom.model.entity.provedor.IConnectionClienteCertified;

public interface IFirewall {
    void lock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void unlock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
}
