package net.servehttp.bytecom.service.provedor;

import java.io.Serializable;

import net.servehttp.bytecom.persistence.jpa.entity.provedor.IConnectionClienteCertified;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.IServer;


public interface IConnectionControl extends Serializable {
  
  void save(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void remove(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void kickout(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  MikrotikConnection setAutoCloseable(boolean autoCloseable) throws Exception;

}
