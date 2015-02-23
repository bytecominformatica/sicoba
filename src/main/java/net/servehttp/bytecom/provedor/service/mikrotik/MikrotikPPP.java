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

  public void salvarSecret(Conexao conexao) throws Exception {
    String profile = conexao.getCliente().getStatus().getProfile(conexao.getCliente());

    if (!buscarProfilePorNome(conexao.getMikrotik(), profile).isEmpty()) {
      if (buscarSecretPorNome(conexao).isEmpty()) {
        execute(
            conexao.getMikrotik(),
            String.format("/ppp/secret/add name=%s password=%s profile=%s service=pppoe",
                conexao.getNome(), conexao.getSenha(), profile));
      } else {
        execute(
            conexao.getMikrotik(),
            String.format("/ppp/secret/set .id=%s password=%s profile=%s service=pppoe",
                conexao.getNome(), conexao.getSenha(), profile));
      }
    } else {
      throw new MensagemException(String.format("Plano %s não encontrado no %s", profile, conexao
          .getMikrotik().getNome()));
    }
  }

  public void desconectar(Conexao conexao) throws Exception {
    if (isActive(conexao)) {
      execute(conexao.getMikrotik(), String.format("/ppp/active/remove numbers=0", conexao.getNome()));
    }
  }
  
  public static void main(String[] args) throws Exception {
    System.out.println("teste");
    Conexao c = new Conexao();
    c.setNome("clairton");
    Mikrotik mk = new Mikrotik();
    mk.setSenha("cecinfo10");
    mk.setHost("192.168.88.1");
    c.setMikrotik(mk);
//    new MikrotikPPP().desconectar(c);
    MikrotikPPP ppp = new MikrotikPPP();
    List<Map<String, String>> res = ppp.execute(mk, "/file/print detail where name=conf.rsc");
    System.out.println(res);
    String text = res.get(0).get("contents");
    for (String line : text.split("\r")) {
    System.out.println(line);
    }
    
  }

  public void removerSecret(Conexao conexao) throws Exception {
    if (!buscarSecretPorNome(conexao).isEmpty()) {
      execute(conexao.getMikrotik(), String.format("/ppp/secret/remove .id=%s", conexao.getNome()));
    }
  }

  public boolean isActive(Conexao conexao) throws Exception {
    List<Map<String, String>> result =
        execute(conexao.getMikrotik(),
            String.format("/ppp/active/print where name=%s", conexao.getNome()));
    return !result.isEmpty();
  }

  public List<Map<String, String>> buscarProfilePorNome(Mikrotik mk, String nome) throws Exception {
    return execute(mk, String.format("/ppp/profile/print where name=%s", nome));
  }

  public List<Map<String, String>> buscarSecretPorNome(Conexao conexao) throws Exception {
    return execute(conexao.getMikrotik(),
        String.format("/ppp/secret/print where name=%s", conexao.getNome()));
  }

}
