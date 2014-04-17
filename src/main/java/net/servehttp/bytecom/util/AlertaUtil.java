package net.servehttp.bytecom.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Clairton Luz
 *
 */
public class AlertaUtil {
	public static final int INFO = 1;
	public static final int WARN = 2;
	public static final int ERROR = 3;

	public static void alerta(String mensagem) {
		alerta(mensagem, INFO);
	}

	public static void alerta(String mensagem, int tipo) {
		FacesMessage msg;
		switch (tipo) {
		case ERROR:
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
			FacesContext.getCurrentInstance().validationFailed();
			break;

		case WARN:
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null);
			break;

		default:
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
			break;
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
