package net.servehttp.bytecom.extra.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import net.servehttp.bytecom.comercial.jpa.entity.Endereco;
import net.servehttp.bytecom.extra.pojo.Location;

public abstract class GoogleMaps {

  private static final String GEOCODE_URL =
      "https://maps.googleapis.com/maps/api/geocode/json?address=%s,%s,%s&sensor=false";

  private static String getPathMap(Endereco endereco) {
    try {
      return String.format(GEOCODE_URL, URLEncoder.encode(endereco.getLogradouro(), "UTF-8"),
          URLEncoder.encode(endereco.getNumero(), "UTF-8"),
          URLEncoder.encode(endereco.getBairro().getNome(), "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Location getLocation(Endereco endereco) {
    Location l = null;
    if (endereco != null) {
      String url = getPathMap(endereco);
      JSONObject json = RestClient.get(url);
      JSONArray results = json.getJSONArray("results");
      if (results.length() > 0) {
        JSONObject location =
            results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        l = new Location(location.getDouble("lat"), location.getDouble("lng"));
      }
    }
    return l;
  }
}
