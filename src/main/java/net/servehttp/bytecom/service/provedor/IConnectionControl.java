package net.servehttp.bytecom.service.provedor;

import java.io.Closeable;

import net.servehttp.bytecom.persistence.jpa.entity.provedor.IConnectionClienteCertified;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.IServer;


public interface IConnectionControl extends Closeable {
  
  IConnectionControl open(IServer server) throws Exception;
  boolean isOpen() throws Exception;
  void setAutoCloseable(boolean autoCloseable) throws Exception;
  void save(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void remove(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void kickout(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void lock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;
  void unlock(IServer server, IConnectionClienteCertified connectionClient) throws Exception;

}
