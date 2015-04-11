package net.servehttp.bytecom.extra.util;

import net.servehttp.bytecom.extra.pojo.Location;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Bairro;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Endereco;

import org.junit.Assert;
import org.junit.Test;


public class GoogleMapsTest {

  @Test
  public void testGetLocation() throws Exception {
    Endereco end = new Endereco();
    end.setLogradouro("Rua 23 de Maio");
    end.setNumero("374");
    Bairro bairro = new Bairro();
    bairro.setNome("Patrícia Gomes");
    end.setBairro(bairro);
    Location location = GoogleMaps.getLocation(end);
    Assert.assertNotNull(location);
    Assert.assertTrue(location.getLat() == -3.7586736);
    Assert.assertTrue(location.getLng() == -38.6527483);
  }

}
