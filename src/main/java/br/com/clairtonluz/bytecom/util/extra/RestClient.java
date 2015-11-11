package br.com.clairtonluz.bytecom.util.extra;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SuppressWarnings("deprecation")
public abstract class RestClient {

    @SuppressWarnings("resource")
    public static JSONObject get(String url) throws IOException {
        StringBuilder sb = new StringBuilder();
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

        return new JSONObject(sb.toString());
    }

    @SuppressWarnings("resource")
    public static JSONArray getArray(String url) throws IOException {
        StringBuilder sb = new StringBuilder();
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


        return new JSONArray(sb.toString());
    }
}
