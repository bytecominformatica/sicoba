package br.com.clairtonluz.sicoba.service.comercial.conexao;

import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.sicoba.service.provedor.IConnectionControl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by clairtonluz on 07/12/15.
 */
public abstract class IConexaoOperacao {
    public static boolean autoCloseable = false;

    @Autowired
    private IConnectionControl server;

    public void executar(Conexao conexao, Plano plano) throws Exception {
        //        if (EnvironmentFactory.create().getEnv() == Environment.PRODUCTION) {
        System.out.println(conexao.getCliente().getStatus() + " " + conexao.getNome() + " - " + conexao.getMikrotik().getName());
        Secret secret = conexao.createSecret(plano);
        perform(server, conexao, secret);
        //        }
    }

    public void remover(Conexao conexao, Plano plano) throws Exception {
        //        if (EnvironmentFactory.create().getEnv() == Environment.PRODUCTION) {
        System.out.println("REMOVER " + conexao.getNome() + " - " + conexao.getMikrotik().getName());
        Secret secret = conexao.createSecret(plano);
        server.remove(conexao.getMikrotik(), secret);
        //        }
    }


    protected abstract void perform(IConnectionControl server, Conexao conexao, Secret secret) throws Exception;
}
