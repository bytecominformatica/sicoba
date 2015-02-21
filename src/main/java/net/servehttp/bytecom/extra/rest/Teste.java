package net.servehttp.bytecom.extra.rest;

import java.io.IOException;

import net.servehttp.bytecom.extra.util.RestClient;

import org.json.JSONObject;


public class Teste {

  public static void main(String[] args) throws IOException {
    String url = "https://maps.googleapis.com/maps/api/geocode/json?address=Rua+23+de+Maio,374,Patr%C3%ADcia+Gomes&sensor=false";
//    String url = "https://maps.googleapis.com/maps/api/geocode/json?address=Rua+2Maio,374,Patr%C3%ADcia+Gomes&sensor=false";
    JSONObject json =
        RestClient
            .get(url);
    JSONObject location = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
    System.out.println(location.getDouble("lat"));
    System.out.println(location.getDouble("lng"));
  }
}
