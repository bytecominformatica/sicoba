package net.servehttp.bytecom.service.provedor;

import java.util.List;
import java.util.Map;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Conexao;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.Mikrotik;
import net.servehttp.bytecom.util.MensagemException;

public class MikrotikPPP extends MikrotikService {

  private static final long serialVersionUID = -8236651201445790623L;

  public List<Map<String, String>> getClienteOnline(Mikrotik mk) throws Exception {
    return execute(mk, "/ppp/active/print");
  }

  public void salvarSecret(Conexao conexao) throws Exception {
    String profile = conexao.getProfile();
    if (!buscarProfilePorNome(conexao.getMikrotik(), profile).isEmpty()) {
      if (buscarSecretPorNome(conexao).isEmpty()) {
        criarSecret(conexao, profile);
      } else {
        atualizarSecret(conexao, profile);
      }
      desconectar(conexao);
    } else {
      throw new MensagemException("Plano %s não encontrado no %s", profile, conexao.getMikrotik()
          .getName());
    }
  }

  public String getIp(Conexao conexao) throws Exception {
    String ip = null;
    if (conexao != null) {
      List<Map<String, String>> result =
          execute(conexao.getMikrotik(), "/ppp/active/print where name=%s", conexao.getNome());
      ip = result.isEmpty() ? null : result.get(0).get("address");
    }
    return ip;

  }

  private void criarSecret(Conexao conexao, String profile) throws Exception {
    execute(conexao.getMikrotik(),
        "/ppp/secret/add name=%s password=%s profile=%s remote-address=%s service=pppoe",
        conexao.getNome(), conexao.getSenha(), profile, conexao.getIp());
  }

  private void atualizarSecret(Conexao conexao, String profile) throws Exception {
    execute(conexao.getMikrotik(),
        "/ppp/secret/set .id=%s password=%s profile=%s remote-address=%s service=pppoe",
        conexao.getNome(), conexao.getSenha(), profile, conexao.getIp());
  }

  public void removerSecret(Conexao conexao) throws Exception {
    if (!buscarSecretPorNome(conexao).isEmpty()) {
      desconectar(conexao);
      execute(conexao.getMikrotik(), String.format("/ppp/secret/remove .id=%s", conexao.getNome()));
    }
  }

  public void desconectar(Conexao conexao) throws Exception {
    List<Map<String, String>> res =
        execute(conexao.getMikrotik(), "/ppp/active/print where name=%s", conexao.getNome());
    for (Map<String, String> r : res) {
      execute(conexao.getMikrotik(), "/ppp/active/remove .id=%s", r.get(".id"));
    }
  }

  public boolean isActive(Conexao conexao) throws Exception {
    List<Map<String, String>> result =
        execute(conexao.getMikrotik(), "/ppp/active/print where name=%s", conexao.getNome());
    return !result.isEmpty();
  }

  public List<Map<String, String>> buscarProfilePorNome(Mikrotik mk, String nome) throws Exception {
    return execute(mk, "/ppp/profile/print where name=%s", nome);
  }

  public List<Map<String, String>> buscarSecretPorNome(Conexao conexao) throws Exception {
    return execute(conexao.getMikrotik(), "/ppp/secret/print where name=%s", conexao.getNome());
  }

}
