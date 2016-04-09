package br.com.clairtonluz.sicoba.service.provedor.impl;

import br.com.clairtonluz.sicoba.model.entity.provedor.IConnectionClienteCertified;
import br.com.clairtonluz.sicoba.model.entity.provedor.IServer;
import br.com.clairtonluz.sicoba.service.provedor.IConnectionControl;
import br.com.clairtonluz.sicoba.service.provedor.MikrotikConnection;
import me.legrange.mikrotik.ApiConnection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PPPoE extends MikrotikConnection implements IConnectionControl {

    @Override
    public void save(IServer server, IConnectionClienteCertified client) throws Exception {
        if (exists(server, client)) {
            update(server, client);
        } else {
            create(server, client);
        }
    }

    private void create(IServer server, IConnectionClienteCertified client) throws Exception {
        String disabled = client.isDisabled() ? "yes" : "no";
        try (ApiConnection con = open(server)) {
            execute("/ppp/secret/add name=%s password=%s profile=%s remote-address=%s service=pppoe disabled=%s",
                    client.getLogin(), client.getPass(), client.getProfile(), client.getIp(), disabled);
        }
    }

    private void update(IServer server, IConnectionClienteCertified client) throws Exception {
        String disabled = client.isDisabled() ? "yes" : "no";
        try (ApiConnection con = open(server)) {
            execute("/ppp/secret/set .id=%s password=%s profile=%s remote-address=%s service=pppoe disabled=%s",
                    client.getLogin(), client.getPass(), client.getProfile(), client.getIp(), disabled);
        }
    }

    public boolean exists(IServer server, IConnectionClienteCertified client) throws Exception {
        boolean result = false;
        try (ApiConnection con = open(server)) {
            result = !execute("/ppp/secret/print where name=%s", client.getLogin()).isEmpty();
        }
        return result;
    }

    @Override
    public void remove(IServer server, IConnectionClienteCertified client) throws Exception {
        try (ApiConnection con = open(server)) {
            execute(String.format("/ppp/secret/remove .id=%s", client.getLogin()));
        }
    }

    @Override
    public void kickout(IServer server, IConnectionClienteCertified client) throws Exception {
        try (ApiConnection con = open(server)) {
            List<Map<String, String>> res = execute("/ppp/active/print where name=%s", client.getLogin());

            for (Map<String, String> r : res) {
                execute("/ppp/active/remove .id=%s", r.get(".id"));
            }
        }
    }

}
