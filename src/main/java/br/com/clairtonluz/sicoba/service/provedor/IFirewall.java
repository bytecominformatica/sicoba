package br.com.clairtonluz.sicoba.service.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.IConnectionClienteCertified;
import br.com.clairtonluz.sicoba.model.entity.provedor.IServer;

public interface IFirewall {
    void lock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

    void unlock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
}
