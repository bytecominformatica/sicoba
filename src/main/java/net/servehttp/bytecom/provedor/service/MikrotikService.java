package net.servehttp.bytecom.provedor.service;

import java.util.List;
import java.util.Map;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

public class MikrotikService {

  private String host;
  private int porta;
  private String usuario;
  private String senha;

  public MikrotikService(String host, int porta, String usuario, String senha) {
    super();
    this.host = host;
    this.porta = porta;
    this.usuario = usuario;
    this.senha = senha;
  }

  public static void main(String... strings) {
    MikrotikService mk = new MikrotikService("192.168.66.1", 8728, "admin", "cecinfo10");
    List<Map<String, String>> result = mk.execute("/ppp/profile/print");
    result.forEach(r -> {
      System.out.printf("Name = %s%n", r.get("name"));
    });
  }

  private List<Map<String, String>> execute(String commando) {
    try {
      ApiConnection con = ApiConnection.connect(host, porta);
      con.login(usuario, senha);
      List<Map<String, String>> result = con.execute(commando);
      con.disconnect();
      return result;
    } catch (MikrotikApiException | InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }


}
