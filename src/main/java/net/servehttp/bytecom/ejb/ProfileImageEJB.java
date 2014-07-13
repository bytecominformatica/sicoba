package net.servehttp.bytecom.ejb;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;

public class ProfileImageEJB implements Serializable {

  private static final long serialVersionUID = 8974017859406844766L;
  private static final Logger LOGGER = Logger.getLogger(ProfileImageEJB.class.getSimpleName());
  private byte[] byteArray;
  
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
  
  public byte[] setThumbnail(byte[] arquivo, String extensao) {
    ImageIcon imageIcon = new ImageIcon(arquivo);
    Image inImage = imageIcon.getImage();
    double scale = (double) 140 / (double) inImage.getWidth(null);

    int scaledW = (int) (scale * inImage.getWidth(null));
    int scaledH = (int) (scale * inImage.getHeight(null));

    BufferedImage outImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);

    AffineTransform tx = new AffineTransform();
    if (scale < 1.0d) {
      tx.scale(scale, scale);
    }

    Graphics2D g2d = outImage.createGraphics();
    g2d.drawImage(inImage, tx, null);
    g2d.dispose();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      ImageIO.write(outImage, extensao, baos);
      return baos.toByteArray();
    } catch (IOException e) {
      System.out.println("Error");
    } finally {
      try {
        baos.close();
      } catch (IOException e) {
        System.out.println("Error");
      }
    }
    return null;
  }
}
