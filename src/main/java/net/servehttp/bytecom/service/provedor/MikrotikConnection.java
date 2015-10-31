package net.servehttp.bytecom.service.provedor;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import net.servehttp.bytecom.model.jpa.entity.provedor.IServer;
import net.servehttp.bytecom.util.MensagemException;
import net.servehttp.bytecom.util.NetworkUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class MikrotikConnection implements Serializable, AutoCloseable {

    private static final long serialVersionUID = -242422545387720478L;
    private ApiConnection con;
    private boolean autoCloseable;

    public MikrotikConnection() {
        autoCloseable = false;
    }

    protected List<Map<String, String>> execute(IServer server, String commando, Object... parametros)
            throws MikrotikApiException, MensagemException, InterruptedException {
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

    protected MikrotikConnection open(IServer server) throws MikrotikApiException, MensagemException {
        if (!NetworkUtil.INSTANCE.ping(server.getHost())) {
            throw new MensagemException(String.format("Mikrotik: %s - %s:%d não disponível",
                    server.getName(), server.getHost(), server.getPort()));
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
