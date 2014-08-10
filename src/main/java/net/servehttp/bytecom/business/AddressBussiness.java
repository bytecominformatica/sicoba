package net.servehttp.bytecom.business;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import net.servehttp.bytecom.persistence.entity.Bairro;
import net.servehttp.bytecom.persistence.entity.Cidade;
import net.servehttp.bytecom.persistence.entity.Estado;
import net.servehttp.bytecom.pojo.EnderecoPojo;

import com.google.gson.Gson;

public class AddressBussiness extends genericoBusiness implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;

  public List<Cidade> findCities() {
    return genericoJPA.buscarTodos(Cidade.class, true, "nome", 200);
  }

  public Bairro findById(int bairroId) {
    return genericoJPA.findById(Bairro.class, bairroId);
  }

  public Bairro findNeighborhoodByName(String name) {
    return genericoJPA.buscarUm("nome", name, Bairro.class);
  }

  public Cidade findCityByName(String name) {
    return genericoJPA.buscarUm("nome", name, Cidade.class);
  }

  public Estado findStateByName(String name) {
    return genericoJPA.buscarUm("nome", name, Estado.class);
  }

  public EnderecoPojo findAddressByCep(String cep) {

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
  public Bairro getNeighborhood(EnderecoPojo enderecoPojo) {
    Bairro neighborhood = null;
    if (enderecoPojo != null) {
      neighborhood = findNeighborhoodByName(enderecoPojo.getBairro());

      if (neighborhood == null) {
        Cidade city = findCityByName(enderecoPojo.getLocalidade());
        if (city != null) {
          neighborhood = new Bairro();
          neighborhood.setNome(enderecoPojo.getBairro());
          neighborhood.setCidade(city);
          salvar(neighborhood);
        } else {
          Estado state = findStateByName(enderecoPojo.getUf());
          if (state != null) {
            Cidade c = new Cidade();
            c.setEstado(state);
            c.setNome(enderecoPojo.getLocalidade());
            salvar(c);

            neighborhood = new Bairro();
            neighborhood.setNome(enderecoPojo.getBairro());
            neighborhood.setCidade(c);
            salvar(neighborhood);
          }
        }
      }
    }
    return neighborhood;
  }

}
