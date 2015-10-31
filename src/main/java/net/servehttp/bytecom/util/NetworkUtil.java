package net.servehttp.bytecom.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author clairtonluz
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
            pingSucess = ipAdress.isReachable(5000);
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
