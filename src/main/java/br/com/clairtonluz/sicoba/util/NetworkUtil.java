package br.com.clairtonluz.sicoba.util;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author clairtonluz
 */
public enum NetworkUtil {

    INSTANCE;

    public String getIp() {
//        HttpServletRequest request = (HttpServletRequest) FacesContext
//                .getCurrentInstance().getExternalContext().getRequest();
//        String ipAddress = request.getHeader("X-FORWARDED-FOR");
//
//        if (ipAddress == null) {
//            ipAddress = request.getRemoteAddr();
//        }
        return "implementar";
    }

    public boolean ping(String ip) throws IOException {
        boolean pingSucess = false;
        InetAddress ipAdress = InetAddress.getByName(ip);
        pingSucess = ipAdress.isReachable(10000);

        return pingSucess;
    }
}
