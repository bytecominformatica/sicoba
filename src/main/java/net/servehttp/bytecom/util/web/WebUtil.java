package net.servehttp.bytecom.util.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Clairton Luz
 *
 */
public abstract class WebUtil implements Serializable {

  private static final long serialVersionUID = -2462589699177123967L;

  public static void redirect(String page) {
    try {
      ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
      ec.redirect(page);
    } catch (IOException e) {
      Logger.getLogger(WebUtil.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public static void downloadPDF(byte[] pdfData, String filename) throws IOException {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

    response.reset();
    response.setContentType("application/pdf");
    response.setHeader("Content-disposition",
        String.format("attachment; filename=\"%s.pdf\"", filename));

    OutputStream output = response.getOutputStream();
    output.write(pdfData);
    output.close();

    facesContext.responseComplete();
  }
  
  public static String getParameters(String name) {
    Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap();
    return requestParams.get(name);
}
}
