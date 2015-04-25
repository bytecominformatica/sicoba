package net.servehttp.bytecom.service.provedor;

import net.servehttp.bytecom.persistence.jpa.entity.provedor.IConnectionClienteCertified;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.IServer;


public interface IConnectionControl {
  
  void save(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void remove(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void kickout(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void lock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void unlock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

}
