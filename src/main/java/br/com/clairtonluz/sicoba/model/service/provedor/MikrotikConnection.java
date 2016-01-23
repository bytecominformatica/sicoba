package br.com.clairtonluz.sicoba.model.service.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.IServer;
import br.com.clairtonluz.sicoba.model.util.NetworkUtil;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class MikrotikConnection implements AutoCloseable {

    private ApiConnection con;
    private boolean autoCloseable;

    public MikrotikConnection() {
        autoCloseable = false;
    }

    protected List<Map<String, String>> execute(IServer server, String commando, Object... parametros)
            throws MikrotikApiException, InterruptedException, IOException {
        commando = String.format(commando, parametros);
        if (!isOpen()) {
            open(server);
            con.login(server.getLogin(), server.getPass());
        }
        List<Map<String, String>> result = con.execute(commando);

        if (!autoCloseable) {
            close();
        }
        return result;
    }

    protected MikrotikConnection open(IServer server) throws MikrotikApiException, IOException {
        if (!NetworkUtil.INSTANCE.ping(server.getHost())) {
//            TODO: MensagemException
//            throw new MensagemException(String.format("Mikrotik: %s - %s:%d não disponível",
//                    server.getName(), server.getHost(), server.getPort()));
        }

        con = ApiConnection.connect(server.getHost(), server.getPort());
        return this;
    }

    public void close() throws MikrotikApiException {
        if (con != null && con.isConnected()) {
            con.disconnect();
        }
    }

    public boolean isOpen() {
        return con != null && con.isConnected();
    }

    public MikrotikConnection setAutoCloseable(boolean autoCloseable) {
        this.autoCloseable = autoCloseable;
        return this;

    }
}
