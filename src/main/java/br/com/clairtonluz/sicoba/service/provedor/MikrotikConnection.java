package br.com.clairtonluz.sicoba.service.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.IServer;
import br.com.clairtonluz.sicoba.util.NetworkUtil;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class MikrotikConnection {

    private ApiConnection con;

    protected List<Map<String, String>> execute(String commando, Object... parametros)
            throws MikrotikApiException, InterruptedException, IOException {
        commando = String.format(commando, parametros);
        List<Map<String, String>> result = con.execute(commando);

        return result;
    }

    protected ApiConnection open(IServer server) throws MikrotikApiException, IOException {
        if (!NetworkUtil.INSTANCE.ping(server.getHost())) {
            throw new RuntimeException(String.format("Mikrotik: %s - %s:%d não disponível",
                    server.getName(), server.getHost(), server.getPort()));
        }
        con = ApiConnection.connect(server.getHost());
        con.login(server.getLogin(), server.getPass());
        return con;
    }

    public boolean isOpen() {
        return con != null && con.isConnected();
    }

}
