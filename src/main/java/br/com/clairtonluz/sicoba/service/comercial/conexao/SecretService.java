package br.com.clairtonluz.sicoba.service.comercial.conexao;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.sicoba.service.provedor.Servidor;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 09/04/16.
 */
public interface SecretService {

    void save(Servidor servidor, Secret... secrets);

    void remove(Servidor servidor, Secret... secrets);
}
