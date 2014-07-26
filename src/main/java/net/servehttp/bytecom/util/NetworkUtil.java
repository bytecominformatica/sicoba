package net.servehttp.bytecom.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Clairton Luz - clairton.c.l@gmail.com
 *
 */
public enum NetworkUtil {

	INSTANCE;

	public String getIp() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	public boolean ping(String ip) {
		boolean pingSucess = false;
		try {
			InetAddress ipAdress = InetAddress.getByName(ip);
			pingSucess = ipAdress.isReachable(50);
		} catch (UnknownHostException ex) {
			Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return pingSucess;
	}
}
