package net.servehttp.bytecom.web.service.maps;

import java.io.Serializable;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import net.servehttp.bytecom.persistence.entity.maps.ClienteGeoReferencia;
/**
 * 
 * @author <a href="mailto:felipewmartins@gmail.com">Felipe W. M. Martins<br>
 *
 */
public class JSONProcessor implements Serializable {

  private static final long serialVersionUID = 4216610710964380446L;
  
  public static String processaJson(ClienteGeoReferencia cliente) throws JSONException{
    JSONObject jObj = new JSONObject();
    jObj.put("lat", cliente.getLatitude());
    jObj.put("lng", cliente.getLongitude());
    
    return jObj.toString();
  }

}
