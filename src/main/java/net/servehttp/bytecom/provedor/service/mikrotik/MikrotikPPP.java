package net.servehttp.bytecom.provedor.service.mikrotik;

import java.util.List;
import java.util.Map;

import net.servehttp.bytecom.comercial.jpa.entity.Conexao;
import net.servehttp.bytecom.provedor.jpa.entity.Mikrotik;
import net.servehttp.bytecom.util.MensagemException;

public class MikrotikPPP extends MikrotikService {

  private static final long serialVersionUID = -8236651201445790623L;

  public List<Map<String, String>> getClienteOnline(Mikrotik mk) throws Exception {
    return execute(mk, "/ppp/active/print");
  }

  public void addSecret(Conexao conexao) throws Exception {
    String plano = conexao.getCliente().getContrato().getPlano().getNome();
    
    if (!buscarProfilePorNome(conexao.getMikrotik(), plano).isEmpty()) {
      execute(
          conexao.getMikrotik(),
          String.format("/ppp/secret/add name=%s password=%s profile=%s service=pppoe",
              conexao.getNome(), conexao.getSenha(), plano));
    } else {
      throw new MensagemException(String.format("Plano %s não encontrado no %s", plano, conexao.getMikrotik().getNome()));
    }
  }

  public void updateSecret(Conexao conexao) throws Exception {
    execute(
        conexao.getMikrotik(),
        String.format("/ppp/secret/set .id=%s password=%s profile=%s service=pppoe",
            conexao.getNome(), conexao.getSenha(), "ilimitada"));
  }

  public void removeSecret(Conexao conexao) throws Exception {
    execute(conexao.getMikrotik(), String.format("/ppp/secret/remove .id=%s", conexao.getNome()));
  }

  public List<Map<String, String>> buscarProfilePorNome(Mikrotik mk, String nome) throws Exception {
    return execute(mk, String.format("/ppp/profile/print where name=%s", nome));
  }

}
