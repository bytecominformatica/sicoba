package net.servehttp.bytecom;

/**
 *
 * @author clairton
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import net.servehttp.bytecom.persistence.entity.Usuario;

public class AuthorizationListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();
		ExternalContext ec = facesContext.getExternalContext();

		String currentPage = facesContext.getViewRoot().getViewId();

		boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > 0);
		HttpSession session = (HttpSession) ec.getSession(true);

		Usuario currentUser = (Usuario) session.getAttribute("currentUser");

		if (!isLoginPage && currentUser == null) {
			try {
				ec.redirect(ec.getApplicationContextPath() + "/login.xhtml");
			} catch (IOException e) {
				Logger.getLogger(AuthorizationListener.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
