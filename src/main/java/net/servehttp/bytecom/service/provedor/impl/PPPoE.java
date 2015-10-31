package net.servehttp.bytecom.service.provedor.impl;

import net.servehttp.bytecom.model.jpa.entity.provedor.IConnectionClienteCertified;
import net.servehttp.bytecom.model.jpa.entity.provedor.IServer;
import net.servehttp.bytecom.service.provedor.IConnectionControl;
import net.servehttp.bytecom.service.provedor.IFirewall;
import net.servehttp.bytecom.service.provedor.MikrotikConnection;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class PPPoE extends MikrotikConnection implements IConnectionControl {

    private static final long serialVersionUID = 1200975817858594209L;

    @Inject
    private IFirewall firewall;

    @Override
    public void save(IServer server, IConnectionClienteCertified client) throws Exception {
        if (exists(server, client)) {
            update(server, client);
        } else {
            create(server, client);
        }
    }

    private void create(IServer server, IConnectionClienteCertified client) throws Exception {
        execute(server,
                "/ppp/secret/add name=%s password=%s profile=%s remote-address=%s service=pppoe",
                client.getLogin(), client.getPass(), client.getProfile(), client.getIp());
    }

    private void update(IServer server, IConnectionClienteCertified client) throws Exception {
        execute(server,
                "/ppp/secret/set .id=%s password=%s profile=%s remote-address=%s service=pppoe",
                client.getLogin(), client.getPass(), client.getProfile(), client.getIp());
    }

    public boolean exists(IServer server, IConnectionClienteCertified client) throws Exception {
        List<Map<String, String>> result =
                execute(server, "/ppp/secret/print where name=%s", client.getLogin());

        return !result.isEmpty();
    }

    @Override
    public void remove(IServer server, IConnectionClienteCertified client) throws Exception {
        firewall.lock(server, client);
        execute(server, String.format("/ppp/secret/remove .id=%s", client.getLogin()));
    }

    @Override
    public void kickout(IServer server, IConnectionClienteCertified client) throws Exception {
        List<Map<String, String>> res =
                execute(server, "/ppp/active/print where name=%s", client.getLogin());

        for (Map<String, String> r : res) {
            execute(server, "/ppp/active/remove .id=%s", r.get(".id"));
        }
    }

}
