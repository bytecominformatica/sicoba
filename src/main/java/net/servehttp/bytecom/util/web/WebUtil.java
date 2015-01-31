package net.servehttp.bytecom.util.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Clairton Luz
 *
 */
public class WebUtil implements Serializable {
	
  private static final long serialVersionUID = -2462589699177123967L;

  public void redirect(String page) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(page);
		} catch (IOException e) {
			Logger.getLogger(WebUtil.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}
