package net.servehttp.bytecom.util.extra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public abstract class RestClient {

  @SuppressWarnings("resource")
  public static JSONObject get(String url) {
    StringBuilder sb = new StringBuilder();
    try {
      HttpClient client = new DefaultHttpClient();
      HttpGet request = new HttpGet(url);
      HttpResponse response;
      response = client.execute(request);
      BufferedReader rd =
          new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      String line = "";
      while ((line = rd.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new JSONObject(sb.toString());
  }

  @SuppressWarnings("resource")
  public static JSONArray getArray(String url) {
    StringBuilder sb = new StringBuilder();
    try {
      HttpClient client = new DefaultHttpClient();
      HttpGet request = new HttpGet(url);
      HttpResponse response;
      response = client.execute(request);
      BufferedReader rd =
          new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      String line = "";
      while ((line = rd.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new JSONArray(sb.toString());
  }
}
