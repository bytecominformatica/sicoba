package br.com.clairtonluz.sicoba.model.service.provedor.impl;

import br.com.clairtonluz.sicoba.model.entity.provedor.IConnectionClienteCertified;
import br.com.clairtonluz.sicoba.model.entity.provedor.IServer;
import br.com.clairtonluz.sicoba.model.service.provedor.IConnectionControl;
import br.com.clairtonluz.sicoba.model.service.provedor.IFirewall;
import br.com.clairtonluz.sicoba.model.service.provedor.MikrotikConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PPPoE extends MikrotikConnection implements IConnectionControl {

    @Autowired
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
