package br.com.clairtonluz.sicoba.service.comercial.conexao.impl;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.sicoba.service.comercial.conexao.SecretService;
import br.com.clairtonluz.sicoba.service.provedor.Servidor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 09/04/16.
 */
@Service
public class SecretPppoeService implements SecretService {

    @Override
    public void save(Servidor servidor, Secret... secrets) {
        for (Secret it : secrets) {
            if (exists(servidor, it)) {
                boolean needReconnect = isActiveProfileWrong(servidor, it);
                update(servidor, it);
                if (needReconnect) disconnect(servidor, it);
            } else {
                create(servidor, it);
            }
        }
    }

    @Override
    public void remove(Servidor servidor, Secret... secrets) {
        for (Secret it : secrets) {
            servidor.execute(String.format("/ppp/secret/remove .id=%s", it.getLogin()));
        }
    }

    private boolean exists(Servidor servidor, Secret secret) {
        String command = String.format("/ppp/secret/print where name=%s", secret.getLogin());
        List result = servidor.execute(command);
        return !result.isEmpty();
    }

    private List getActive(Servidor servidor, Secret secret) {
        String command = String.format("/ppp/active/print where name=%s", secret.getLogin());
        return servidor.execute(command);
    }

    private boolean isActiveProfileWrong(Servidor servidor, Secret secret) {
        return isActive(servidor, secret) && isDifferentProfile(servidor, secret);
    }

    private boolean isActive(Servidor servidor, Secret secret) {
        List activeList = getActive(servidor, secret);
        return !activeList.isEmpty();
    }

    private boolean isDifferentProfile(Servidor servidor, Secret secret) {
        List withSameProfile = servidor.execute(String.format("/ppp/secret/print where name=%s and profile=%s", secret.getLogin(), secret.getProfile()));
        return withSameProfile.isEmpty();
    }


    private void create(Servidor servidor, Secret secret) {
        String disabled = secret.isDisabled() ? "yes" : "no";
        String command = String.format("/ppp/secret/add name=%s password=%s profile=%s remote-address=%s service=pppoe disabled=%s caller-id=\"%s\"",
                secret.getLogin(), secret.getPass(), secret.getProfile(), secret.getIp(), disabled, secret.getMac());
        servidor.execute(command);
    }

    private void update(Servidor servidor, Secret secret) {
        String disabled = secret.isDisabled() ? "yes" : "no";
        String command = String.format("/ppp/secret/set .id=%s password=%s profile=%s remote-address=%s service=pppoe disabled=%s caller-id=\"%s\"",
                secret.getLogin(), secret.getPass(), secret.getProfile(), secret.getIp(), disabled, secret.getMac());

        servidor.execute(command);

        if (secret.isDisabled()) {
            disconnect(servidor, secret);
        }
    }

    private void disconnect(Servidor servidor, Secret secret) {
        List<Map<String, String>> result = getActive(servidor, secret);
        if (!result.isEmpty()) {
            result.forEach(active -> {
                String command = String.format("/ppp/active/remove .id=%s", active.get(".id"));
                servidor.execute(command);
            });
        }
    }
}
