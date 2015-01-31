package net.servehttp.bytecom.util.web;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Clairton Luz
 *
 */
public abstract class AlertaUtil {

  public static void info(String mensagem) {
    alert(mensagem, FacesMessage.SEVERITY_INFO);
  }

  public static void warn(String mensagem) {
    alert(mensagem, FacesMessage.SEVERITY_WARN);
  }

  public static void error(String mensagem) {
    alert(mensagem, FacesMessage.SEVERITY_ERROR);
    FacesContext.getCurrentInstance().validationFailed();
  }

  private static void alert(String mensagem, Severity type) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(type, mensagem, null));
  }

}
