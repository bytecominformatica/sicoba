package net.servehttp.bytecom.util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Util {

  public String active(String path, String menu) {
    return path.contains(menu) ? "active" : "";
  }
}
