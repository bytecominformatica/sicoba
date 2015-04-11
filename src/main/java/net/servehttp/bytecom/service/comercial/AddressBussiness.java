package net.servehttp.bytecom.service.comercial;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import net.servehttp.bytecom.persistence.jpa.comercial.EnderecoJPA;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Bairro;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cidade;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Estado;
import net.servehttp.bytecom.pojo.comercial.EnderecoPojo;

import com.google.gson.Gson;

public class AddressBussiness implements Serializable {

  private static final long serialVersionUID = -8296012997453708684L;

  @Inject
  private EnderecoJPA enderecoJPA;
  
  public List<Cidade> findCities() {
    return enderecoJPA.buscarTodos(Cidade.class, true, "nome", 200);
  }

  public Bairro buscarPorId(int bairroId) {
    return enderecoJPA.buscarPorId(Bairro.class, bairroId);
  }

  public Bairro findNeighborhoodByName(String name) {
    return enderecoJPA.buscarUm("nome", name, Bairro.class);
  }

  public Cidade findCityByName(String name) {
    return enderecoJPA.buscarUm("nome", name, Cidade.class);
  }

  public Estado findStateByName(String name) {
    return enderecoJPA.buscarUm("nome", name, Estado.class);
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
          enderecoJPA.salvar(neighborhood);
        } else {
          Estado state = findStateByName(enderecoPojo.getUf());
          if (state != null) {
            Cidade c = new Cidade();
            c.setEstado(state);
            c.setNome(enderecoPojo.getLocalidade());
            enderecoJPA.salvar(c);

            neighborhood = new Bairro();
            neighborhood.setNome(enderecoPojo.getBairro());
            neighborhood.setCidade(c);
            enderecoJPA.salvar(neighborhood);
          }
        }
      }
    }
    return neighborhood;
  }

}
