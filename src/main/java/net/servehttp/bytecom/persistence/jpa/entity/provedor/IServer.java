package net.servehttp.bytecom.persistence.jpa.entity.provedor;

import java.io.Serializable;

public interface IServer extends Serializable {

  String getHost();
  String getLogin();
  String getPass();
  int getPort();
  String getName();
  String getDescription();
}
