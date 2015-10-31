package net.servehttp.bytecom.util;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Logger;

/**
 * @author felipe
 */
public abstract class ImageUtil implements Serializable {

    private static final long serialVersionUID = 8974017859406844766L;
    private static final Logger LOGGER = Logger.getLogger(ImageUtil.class.getName());

    public static byte[] tratarImagem(InputStream is) {
        byte[] byteArray = null;
        if (is != null) {
            try {
                byteArray = IOUtils.toByteArray(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArray;
    }

    public static byte[] setThumbnail(byte[] arquivo, String extensao) {
        ImageIcon imageIcon = new ImageIcon(arquivo);
        Image inImage = imageIcon.getImage();
        double scale = 100 / (double) inImage.getWidth(null);

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
     * Converte os bytesImage para um arquivo de imagem e o salva no diretório passado
     * no folderImage com o nome passado no filename e retorna o caminho completo da imagem. <br/>
     * Por exemplo: Se passar o folder 'bytecom/img/' e passar no parametro filename 'image.png' então
     * será criado uma imagem neste diretório com esse nome e é retornado a String 'bytecom/img/image.png'
     * </pre>
     *
     * @param bytesImage
     * @param filename
     * @param folderImages
     * @return String
     */
    public static String exibirImagem(byte[] bytesImage, String filename, String folderImages) {
        if (bytesImage == null) {
            return null;
        }
        String path = "";
        try {
            File dirImageUsers = new File(folderImages);

            if (!dirImageUsers.exists()) {
                dirImageUsers.createNewFile();
            }

            byte[] bytes = bytesImage;
            FileImageOutputStream imageOutput =
                    new FileImageOutputStream(new File(dirImageUsers, filename));
            imageOutput.write(bytes, 0, bytes.length);
            imageOutput.flush();
            imageOutput.close();
            path = folderImages + filename;
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        return path;
    }
}
