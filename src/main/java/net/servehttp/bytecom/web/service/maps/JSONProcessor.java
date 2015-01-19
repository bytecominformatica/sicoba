package net.servehttp.bytecom.web.service.maps;

import java.io.Serializable;
import java.util.List;

import net.servehttp.bytecom.persistence.entity.maps.ClienteGeoReferencia;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 
 * @author <a href="mailto:felipewmartins@gmail.com">Felipe W. M. Martins<br>
 *
 */
public class JSONProcessor implements Serializable {

  private static final long serialVersionUID = 4216610710964380446L;


  public static String processaJson(List<ClienteGeoReferencia> listClientesGeorefs) {
    JSONObject jObj = new JSONObject();
    try {

      for (ClienteGeoReferencia clienteGeoReferencia : listClientesGeorefs) {
        jObj.put("lat", clienteGeoReferencia.getLatitude());
        jObj.put("lng", clienteGeoReferencia.getLongitude());
      }
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return jObj.toString();
  }

}
