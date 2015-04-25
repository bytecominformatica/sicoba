package net.servehttp.bytecom.service.provedor;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.IConnectionClienteCertified;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.IServer;
import net.servehttp.bytecom.util.MensagemException;
import net.servehttp.bytecom.util.NetworkUtil;

public class PPPoE implements IConnectionControl {

  private static final long serialVersionUID = -2374802578829013911L;

  @Override
  public void save(IServer server, IConnectionClienteCertified client) throws Exception {
    if (exists(server, client)) {
      update(server, client);
    } else {
      create(server, client);
    }
  }

  private void create(IServer server, IConnectionClienteCertified client) throws Exception {
    execute(server,
        "/ppp/secret/add name=%s password=%s profile=%s remote-address=%s service=pppoe",
        client.getLogin(), client.getPass(), client.getProfile(), client.getIp());
  }

  private void update(IServer server, IConnectionClienteCertified client) throws Exception {
    execute(server,
        "/ppp/secret/set .id=%s password=%s profile=%s remote-address=%s service=pppoe",
        client.getLogin(), client.getPass(), client.getProfile(), client.getIp());
  }

  public boolean exists(IServer server, IConnectionClienteCertified client) throws Exception {
    List<Map<String, String>> result =
        execute(server, "/ppp/secret/print where name=%s", client.getLogin());

    return !result.isEmpty();
  }

  @Override
  public void remove(IServer server, IConnectionClienteCertified client) throws Exception {
    execute(server, String.format("/ppp/secret/remove .id=%s", client.getLogin()));
  }

  @Override
  public void kickout(IServer server, IConnectionClienteCertified client) throws Exception {
    List<Map<String, String>> res =
        execute(server, "/ppp/active/print where name=%s", client.getLogin());

    for (Map<String, String> r : res) {
      execute(server, "/ppp/active/remove .id=%s", r.get(".id"));
    }
  }
  
  @Override
  public void lock(IServer server, IConnectionClienteCertified client) throws Exception {
    execute(server, "/ip/firewall/filter/add chain=forward src-address=%s dst-address=!%s action=drop",
        client.getIp(), Inet4Address.getLocalHost().getHostAddress());
  }

  @Override
  public void unlock(IServer server, IConnectionClienteCertified client) throws Exception {
    List<Map<String, String>> res =
        execute(server, "/ip/firewall/filter/print where src-address=%s", client.getIp());

    for (Map<String, String> r : res) {
      execute(server, "/ip/firewall/filter/remove .id=%s", r.get(".id"));
    }
  }

  private List<Map<String, String>> execute(IServer server, String commando, Object... parametros)
      throws Exception {
    commando = String.format(commando, parametros);
    ApiConnection con = connect(server);
    con.login(server.getLogin(), server.getPass());
    List<Map<String, String>> result = con.execute(commando);
    con.disconnect();
    return result;
  }

  private ApiConnection connect(IServer server) throws MikrotikApiException, MensagemException {
    if (!NetworkUtil.INSTANCE.ping(server.getHost())) {
      throw new MensagemException(String.format("Mikrotik: %s - %s:%d não disponível",
          server.getName(), server.getHost(), server.getPort()));
    }

    return ApiConnection.connect(server.getHost(), server.getPort());
  }

}
