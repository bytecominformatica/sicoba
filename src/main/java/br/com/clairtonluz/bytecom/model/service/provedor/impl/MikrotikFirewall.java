package br.com.clairtonluz.bytecom.model.service.provedor.impl;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.IConnectionClienteCertified;
import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.IServer;
import br.com.clairtonluz.bytecom.model.service.provedor.IFirewall;
import br.com.clairtonluz.bytecom.model.service.provedor.MikrotikConnection;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;

public class MikrotikFirewall extends MikrotikConnection implements IFirewall {

    private static final long serialVersionUID = 3747587507078798936L;

    @Override
    public void lock(IServer server, IConnectionClienteCertified client) throws Exception {
        List<Map<String, String>> res =
                execute(server, "/ip/firewall/filter/print where src-address=%s or dst-address=%s",
                        client.getIp(), client.getIp());

        for (Map<String, String> r : res) {
            execute(server, "/ip/firewall/filter/remove .id=%s", r.get(".id"));
        }
    }

    @Override
    public void unlock(IServer server, IConnectionClienteCertified client) throws Exception {
        lock(server, client);

        List<Map<String, String>> res =
                execute(server, "/ip/firewall/filter/print where comment=BLOCKALL");

        for (Map<String, String> r : res) {
            execute(server, "/ip/firewall/filter/remove .id=%s", r.get(".id"));
        }

        execute(server, "/ip/firewall/filter/add chain=forward src-address=%s action=accept",
                client.getIp());
        execute(server, "/ip/firewall/filter/add chain=forward dst-address=%s action=accept",
                client.getIp());
        String serverAddress = Inet4Address.getLocalHost().getHostAddress();
        execute(
                server,
                "/ip/firewall/filter/add chain=forward src-address=!%s dst-address=!%s action=drop comment=BLOCKALL",
                serverAddress, serverAddress);
    }

}
