package net.servehttp.bytecom.util.extra;

import net.servehttp.bytecom.util.extra.RestClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;


public class RestClientTest {

  @Test
  public void testGetArray() throws Exception {
    JSONArray array = RestClient.getArray("https://api.github.com/users/clairtonluz/repos");
    Assert.assertNotNull(array);
    Assert.assertTrue(array.length() > 0);
  }

  @Test
  public void testGet() throws Exception {
    JSONObject jsonObject = RestClient.get("https://maps.googleapis.com/maps/api/geocode/json?address=Rua+23+de+Maio,374,Patr%C3%ADcia+Gomes&sensor=false");
    Assert.assertNotNull(jsonObject);
  }

}
