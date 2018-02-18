package br.com.clairtonluz.sicoba.util;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author clairtonluz
 */
public enum NetworkUtil {

    INSTANCE;

    public String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public boolean ping(String ip) throws IOException {
        boolean pingSucess = false;
        InetAddress ipAdress = InetAddress.getByName(ip);
        pingSucess = ipAdress.isReachable(10000);

        return pingSucess;
    }
}
