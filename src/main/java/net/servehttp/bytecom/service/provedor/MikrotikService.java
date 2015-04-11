package net.servehttp.bytecom.service.provedor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import me.legrange.mikrotik.ApiConnection;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.Mikrotik;
import net.servehttp.bytecom.util.MensagemException;
import net.servehttp.bytecom.util.NetworkUtil;

public abstract class MikrotikService implements Serializable {

  private static final long serialVersionUID = -242422545387720478L;

  protected List<Map<String, String>> execute(Mikrotik mk, String commando, Object...parametros) throws Exception {
    commando = String.format(commando, parametros);
    ApiConnection con = connect(mk);
    con.login(mk.getUsuario(), mk.getSenha());
    List<Map<String, String>> result = con.execute(commando);
    con.disconnect();
    return result;
  }
  
  protected List<Map<String, String>> executeManual(ApiConnection con, String commando, Object...parametros) throws Exception {
    commando = String.format(commando, parametros);
    List<Map<String, String>> result = con.execute(commando);
    return result;
  }

  protected ApiConnection connect(Mikrotik mk) throws Exception {
    if (NetworkUtil.INSTANCE.ping(mk.getHost())) {
      return ApiConnection.connect(mk.getHost(), mk.getPorta());
    } else {
      throw new MensagemException(String.format("Mikrotik: %s - %s:%d não disponível", mk.getNome(), mk.getHost(),
          mk.getPorta()));
    }
  }

}
