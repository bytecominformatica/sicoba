package br.com.clairtonluz.sicoba.service.comercial.conexao.impl;

import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.sicoba.service.comercial.conexao.IConexaoOperacao;
import br.com.clairtonluz.sicoba.service.provedor.IConnectionControl;
import br.com.clairtonluz.sicoba.service.provedor.IFirewall;
import br.com.clairtonluz.sicoba.service.provedor.impl.MikrotikFirewall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by clairtonluz on 07/12/15.
 */
@Service
public class ConexaoOperacaoAtivo implements IConexaoOperacao {

    private static final IFirewall FIREWALL = new MikrotikFirewall();
    @Autowired
    private IConnectionControl server;

    @Override
    public void executar(Conexao conexao, Plano plano) throws Exception {
//        if (EnvironmentFactory.create().getEnv() == Environment.PRODUCTION) {
        System.out.println("ATIVO " + conexao.getNome());
        Secret secret = conexao.createSecret(plano);
        server.save(conexao.getMikrotik(), secret);
//            FIREWALL.unlock(conexao.getMikrotik(), secret);
//        }
    }
}
