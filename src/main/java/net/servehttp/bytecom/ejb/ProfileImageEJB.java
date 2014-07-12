package net.servehttp.bytecom.ejb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

public class ProfileImageEJB implements Serializable {

  private static final long serialVersionUID = 8974017859406844766L;
  private static final Logger LOGGER = Logger.getLogger(ProfileImageEJB.class.getSimpleName());
  private byte[] byteArray;
  private BufferedImage bufImage;
  
  public byte[] tratarImagem(Part file){
    try{
      if(file != null){
        InputStream is = file.getInputStream();
        byteArray = IOUtils.toByteArray(is);
      }else{
        byteArray = null;
      }
    }catch(IOException e){
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
   
   return byteArray;
  }
  
  public BufferedImage exibirImage(byte[] regImage){
    try{
      bufImage = ImageIO.read(new ByteArrayInputStream(regImage));
    }catch(IOException e){
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return bufImage;
    
  }
}
