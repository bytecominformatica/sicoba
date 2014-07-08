package net.servehttp.bytecom.ejb;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

public class ProfileImageEJB implements Serializable {

  private static final long serialVersionUID = 8974017859406844766L;
  //private static BufferedImage imagem;
  private static byte[] byteArray;
  
  public byte[] tratarImagem(Part file){
    try{
      InputStream is = file.getInputStream();
      byteArray = IOUtils.toByteArray(is);
    }catch(IOException e){
      
    }
    return byteArray;
  }
}
