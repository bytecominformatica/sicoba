package net.servehttp.bytecom.util;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Bairro;
import net.servehttp.bytecom.persistence.entity.Cidade;
import net.servehttp.bytecom.persistence.entity.Estado;
import net.servehttp.bytecom.pojo.EnderecoPojo;

import com.google.gson.Gson;

public class EnderecoUtil implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6641797106513004435L;
  @Inject
  private GenericoJPA genericoJPA;

  public EnderecoPojo getEndereco(String cep) {

    EnderecoPojo e = null;
    if (cep != null && cep.length() >= 8) {
      String path = "http://viacep.com.br/ws/" + cep + "/json/";

      Client client = ClientBuilder.newClient();
      String resultado = client.target(path).request("text/plain").get(String.class);

      if (resultado.contains("\"erro\":true")) {
        e = new EnderecoPojo();
        e.setCep(cep);
      }

      Gson g = new Gson();

      e = g.fromJson(resultado, EnderecoPojo.class);
    }
    return e;
  }

  /**
   * Verifica se o bairro já existe, se o bairro não existe o mesmo é cadastrado.
   * 
   * @param enderecoPojo
   * @return
   */
  public Bairro getBairro(EnderecoPojo enderecoPojo) {
    Bairro bairro = null;
    if (enderecoPojo != null) {
      List<Bairro> bairros =
          genericoJPA.buscarTodos("nome", enderecoPojo.getBairro(), Bairro.class);
      if (bairros != null && !bairros.isEmpty()) {
        bairro = bairros.get(0);
      } else {
        List<Cidade> cidades =
            genericoJPA.buscarTodos("nome", enderecoPojo.getLocalidade(), Cidade.class);
        if (cidades != null && !cidades.isEmpty()) {
          bairro = new Bairro();
          bairro.setNome(enderecoPojo.getBairro());
          bairro.setCidade(cidades.get(0));
          genericoJPA.salvar(bairro);
        } else {
          List<Estado> estados = genericoJPA.buscarTodos("uf", enderecoPojo.getUf(), Estado.class);
          if (estados != null && !estados.isEmpty()) {
            Cidade c = new Cidade();
            c.setEstado(estados.get(0));
            c.setNome(enderecoPojo.getLocalidade());
            genericoJPA.salvar(c);
            System.out.println("ID DA CIDADE NOVA = " + c.getId());

            bairro = new Bairro();
            bairro.setNome(enderecoPojo.getBairro());
            bairro.setCidade(c);
            genericoJPA.salvar(bairro);
          }
        }
      }
    }
    return bairro;
  }
}
