package br.com.clairtonluz.sicoba.service.comercial.conexao.impl;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.sicoba.service.comercial.conexao.SecretService;
import br.com.clairtonluz.sicoba.service.provedor.Servidor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 09/04/16.
 */
@Service
public class SecretPppoeService implements SecretService {

    @Override
    public void save(Servidor servidor, Secret... secrets) {
        for (Secret it : secrets) {
            if (exists(servidor, it)) {
                update(servidor, it);
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

    private void create(Servidor servidor, Secret secret) {
        String disabled = secret.isDisabled() ? "yes" : "no";
        String command = String.format("/ppp/secret/add name=%s password=%s profile=%s remote-address=%s service=pppoe disabled=%s",
                secret.getLogin(), secret.getPass(), secret.getProfile(), secret.getIp(), disabled);
        servidor.execute(command);
    }

    private void update(Servidor servidor, Secret secret) {
        String disabled = secret.isDisabled() ? "yes" : "no";
        String command = String.format("/ppp/secret/set .id=%s password=%s profile=%s remote-address=%s service=pppoe disabled=%s",
                secret.getLogin(), secret.getPass(), secret.getProfile(), secret.getIp(), disabled);

        if (secret.isDisabled()) {
            desconect(servidor, secret);
        }
        servidor.execute(command);
    }


    private void desconect(Servidor servidor, Secret secret) {
        String command = String.format("/ppp/active/remove .id=%s", secret.getLogin());
        servidor.execute(command);
    }
}
