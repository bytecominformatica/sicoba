package net.servehttp.bytecom.web.service.maps;

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
public class XMLProcessor {
  
  XPathFactory factory = XPathFactory.newInstance();

  XPath xpath = factory.newXPath();
  
  private static String XPATH_EXPRESSION = "/GeocodeResponse//location/text()";
  private String[] latlng = new String[3];


  
  public void xmlRequest(String uri){
    
    try {
      URL url = new URL(uri);
      HttpsURLConnection connection =
          (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/xml");

      InputStream xml = connection.getInputStream();

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(xml);
      xmlParser(doc);
      
    } catch (Exception e) {
      e.printStackTrace();// TODO: handle exception
    }
  }
  
  private void xmlParser(Document doc){
    try {
      NodeList nodes = (NodeList) xpath.evaluate(XPATH_EXPRESSION, doc, XPathConstants.NODESET);
      
      for (int i = 0, n = nodes.getLength(); i < n; i++) {
        //String nodeString = nodes.item(i).getTextContent();
        latlng[0] = nodes.item(i).getTextContent();
        //latlng[1] = nodes.item(i).getTextContent();
        for (int j = 0; j < latlng.length; j++) {
          System.out.print(latlng[i]);
        }
        
        //System.out.print("\n");
      }

    } catch (XPathExpressionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
