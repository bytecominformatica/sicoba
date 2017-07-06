package br.com.clairtonluz.sicoba.service.provedor.impl;

import br.com.clairtonluz.sicoba.config.Environment;
import br.com.clairtonluz.sicoba.config.EnvironmentFactory;
import br.com.clairtonluz.sicoba.service.provedor.Servidor;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.stereotype.Service;

import javax.net.SocketFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.legrange.mikrotik.ApiConnection.DEFAULT_COMMAND_TIMEOUT;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 09/04/16.
 */
@Service
public class ServidorMikrotik implements Servidor {

    private ApiConnection con;

    @Override
    public ApiConnection connect(String host, int port, String user, String pass) {
        if (Environment.isProduction()) {
            try {
                con = ApiConnection.connect(SocketFactory.getDefault(), host, port, DEFAULT_COMMAND_TIMEOUT);
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
        if (Environment.isProduction()) {
            try {
                System.out.println(command);
                return con.execute(command);
            } catch (MikrotikApiException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            String env = EnvironmentFactory.create().getEnv();
            System.out.println("Ambiente: " + env + " não deve lançar comando para o servidor");
        }
        return new ArrayList<>();
    }

    @Override
    public void close() throws Exception {
        if (con != null && con.isConnected()) {
            con.close();
        }
    }
}
