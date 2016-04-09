package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.sicoba.service.comercial.conexao.SecretService;
import br.com.clairtonluz.sicoba.service.provedor.Servidor;

public enum StatusCliente {
    INATIVO {
        @Override
        public void atualizarSecret(SecretService secretService, Servidor servidor, Secret secret) {
            secretService.save(servidor, secret);
        }
    }, ATIVO {
        @Override
        public void atualizarSecret(SecretService secretService, Servidor servidor, Secret secret) {
            secretService.save(servidor, secret);
        }
    }, CANCELADO {
        @Override
        public void atualizarSecret(SecretService secretService, Servidor servidor, Secret secret) {
            secretService.remove(servidor, secret);
        }
    };

    public abstract void atualizarSecret(SecretService secretService, Servidor servidor, Secret secret);
}
