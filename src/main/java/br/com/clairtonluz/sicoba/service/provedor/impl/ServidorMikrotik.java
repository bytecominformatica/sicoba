package br.com.clairtonluz.sicoba.service.provedor.impl;

import br.com.clairtonluz.sicoba.config.Environment;
import br.com.clairtonluz.sicoba.config.EnvironmentFactory;
import br.com.clairtonluz.sicoba.service.provedor.Servidor;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 09/04/16.
 */
@Service
public class ServidorMikrotik implements Servidor {

    private ApiConnection con;

    @Override
    public ApiConnection connect(String host, String user, String pass) {
        if (EnvironmentFactory.create().getEnv() == Environment.PRODUCTION) {
            try {
                con = ApiConnection.connect(host);
                con.setTimeout(10000);
                con.login(user, pass);
            } catch (MikrotikApiException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return con;
    }

    @Override
    public List<Map<String, String>> execute(String command) {
        if (EnvironmentFactory.create().getEnv() == Environment.PRODUCTION) {
            try {
                System.out.println(command);
                return con.execute(command);
            } catch (MikrotikApiException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            System.out.println("Ambiente: " + EnvironmentFactory.create().getEnv() + " não deve lançar comando para o servidor");
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (con != null && con.isConnected()) {
            con.close();
        }
    }
}
