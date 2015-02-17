package net.servehttp.bytecom.provedor.service;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import me.legrange.mikrotik.ApiConnection;
import net.servehttp.bytecom.comercial.jpa.entity.Conexao;
import net.servehttp.bytecom.provedor.jpa.entity.Mikrotik;
import net.servehttp.bytecom.util.NetworkUtil;

public class MikrotikService implements Serializable {

  private static final long serialVersionUID = -242422545387720478L;

  public List<Map<String, String>> getClienteOnline(Mikrotik mk) throws Exception {
    return execute(mk, "/ppp/active/print");
  }

  public void addConexao(Conexao conexao) throws Exception {
    execute(
        conexao.getMikrotik(),
        String.format("/ppp/secret/add name=%s password=%s profile=%s service=pppoe",
            conexao.getNome(), conexao.getSenha(), "ilimitada"));
  }

  public void atualizarConexao(Conexao conexao) throws Exception {
    execute(
        conexao.getMikrotik(),
        String.format("/ppp/secret/set .id=%s password=%s profile=%s service=pppoe",
            conexao.getNome(), conexao.getSenha(), "ilimitada"));
  }

  public void removeConexao(Conexao conexao) throws Exception {
    execute(conexao.getMikrotik(), String.format("/ppp/secret/remove .id=%s", conexao.getNome()));
  }

  private List<Map<String, String>> execute(Mikrotik mk, String commando) throws Exception {
    ApiConnection con = connect(mk);
    con.login(mk.getUsuario(), mk.getSenha());
    List<Map<String, String>> result = con.execute(commando);
    con.disconnect();
    return result;
  }

  private ApiConnection connect(Mikrotik mk) throws Exception {
    if (NetworkUtil.INSTANCE.ping(mk.getHost())) {
      return ApiConnection.connect(mk.getHost(), mk.getPorta());
    } else {
      throw new UnknownHostException(String.format("Mikrotik: %s:%d não disponível", mk.getHost(),
          mk.getPorta()));
    }
  }

}
