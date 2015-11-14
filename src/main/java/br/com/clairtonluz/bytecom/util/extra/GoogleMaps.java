package br.com.clairtonluz.bytecom.util.extra;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Bairro;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Endereco;
import br.com.clairtonluz.bytecom.pojo.extra.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

public abstract class GoogleMaps {

    private static final String GEOCODE_URL =
            "https://maps.googleapis.com/maps/api/geocode/json?address=%s,%s,%s&sensor=false";

    private static String getPathMap(Endereco endereco) throws UnsupportedEncodingException {
        Optional<Endereco> ende = Optional.ofNullable(endereco);
        return String
                .format(GEOCODE_URL,
                        URLEncoder.encode(ende.map(Endereco::getLogradouro).orElse(""), "UTF-8"),
                        URLEncoder.encode(ende.map(Endereco::getNumero).orElse(""), "UTF-8"),
                        URLEncoder.encode(ende.map(Endereco::getBairro).map(Bairro::getNome).orElse(""), "UTF-8"));

    }

    public static Location getLocation(Endereco endereco) throws IOException {
        Location l = null;
        if (endereco != null) {
            String url = getPathMap(endereco);
//            JSONObject json = RestClient.get(url);
//            JSONArray results = json.getJSONArray("results");
//            if (results.length() > 0) {
//                JSONObject location =
//                        results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
//                l = new Location(location.getDouble("lat"), location.getDouble("lng"));
//            }
        }
        return l;
    }
}
