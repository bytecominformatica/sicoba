package net.servehttp.bytecom.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author felipe
 * 
 */

public class ImageUtil implements Serializable {

  private static final long serialVersionUID = 8974017859406844766L;
  private static final Logger LOGGER = Logger.getLogger(ImageUtil.class.getName());
  private byte[] byteArray;

  public byte[] tratarImagem(Part file) {
    try {
      if (file != null) {
        InputStream is = file.getInputStream();
        byteArray = IOUtils.toByteArray(is);
      } else {
        byteArray = null;
      }
    } catch (IOException e) {

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

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(outImage, extensao, baos);
      return baos.toByteArray();
    } catch (IOException e) {
      LOGGER.severe(e.getMessage());
    }
    return null;
  }

  /**
   * <pre>
   * Método responsável por montar uma imagem a partir de um path especifico
   * @param bytesImagem
   * @param id
   * @return path
   * </pre>
   */
  public String exibirImagem(byte[] bytesImagem, long id) {
    String path = null;
    try {
      FacesContext context = FacesContext.getCurrentInstance();
      ServletContext servletcontext = (ServletContext) context.getExternalContext().getContext();
      String imageUsers = servletcontext.getRealPath("/img/users/");
      File dirImageUsers = new File(imageUsers);
      System.out.println(dirImageUsers);

      if (!dirImageUsers.exists()) {
        System.out.println("Diretorio existe");
        dirImageUsers.createNewFile();
      }

      byte[] bytes = bytesImagem;
      FileImageOutputStream imageOutput =
          new FileImageOutputStream(new File(dirImageUsers, id + "." + "png"));
      imageOutput.write(bytes, 0, bytes.length);
      imageOutput.flush();
      imageOutput.close();
      path = "/img/users/" + id + "." + "png";

    } catch (IOException e) {
      LOGGER.severe(e.getMessage());
    }
    return path;
  }
}
