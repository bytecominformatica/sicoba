package net.servehttp.bytecom.util.extra;

import net.servehttp.bytecom.model.jpa.entity.comercial.Bairro;
import net.servehttp.bytecom.model.jpa.entity.comercial.Endereco;
import net.servehttp.bytecom.pojo.extra.Location;
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
        Assert.assertEquals(-3.7586737, location.getLat(), 0d);
        Assert.assertEquals(-38.6527485, location.getLng(), 0d);
    }

}
