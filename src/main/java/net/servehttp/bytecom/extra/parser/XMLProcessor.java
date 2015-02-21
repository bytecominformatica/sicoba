package net.servehttp.bytecom.extra.parser;

import java.io.InputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Felipe W. M Martins
 *
 */
public enum XMLProcessor {
  INSTANCE;
  private static String LAT = "/GeocodeResponse//location/lat";
  private static String LNG = "/GeocodeResponse//location/lng";
  private String[] latlng = new String[2];

  XPathFactory factory = XPathFactory.newInstance();

  XPath xpath = factory.newXPath();

  /**
   * 
   * @param uri
   * @return array de lat e long, respectivamente.
   */
  public String[] xmlRequest(String uri) {
    try {
      URL url = new URL(uri);
      System.out.println(url);
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/xml");

      InputStream xml = connection.getInputStream();

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(xml);
      xmlParser(doc);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return latlng;
  }

  /**
   * 
   * @param doc
   * @return array de latitude e longitude nas posições 0 e 1 respectivamente.
   */
  private String[] xmlParser(Document doc) {
    try {
      NodeList nodes = (NodeList) xpath.evaluate(LAT, doc, XPathConstants.NODE);

      for (int i = 0, n = nodes.getLength(); i < n; i++) {
        latlng[0] = nodes.item(i).getNodeValue();
      }

    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }

    try {
      NodeList nodes = (NodeList) xpath.evaluate(LNG, doc, XPathConstants.NODE);

      for (int i = 0, n = nodes.getLength(); i < n; i++) {
        latlng[1] = nodes.item(i).getNodeValue();

      }
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    return latlng;


  }
}
