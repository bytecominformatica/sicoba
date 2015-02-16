package net.servehttp.bytecom.provedor.service;

import java.util.List;
import java.util.Map;

import net.servehttp.bytecom.provedor.jpa.entity.Mikrotik;
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

  public MikrotikService(Mikrotik mikrotik) {
    super();
    this.host = mikrotik.getHost();
    this.porta = mikrotik.getPorta();
    this.usuario = mikrotik.getUsuario();
    this.senha = mikrotik.getSenha();
  }

  public List<Map<String, String>> execute(String commando) throws MikrotikApiException, InterruptedException {
    ApiConnection con = ApiConnection.connect(host, porta);
    con.login(usuario, senha);
    List<Map<String, String>> result = con.execute(commando);
    con.disconnect();
    return result;
  }


}
