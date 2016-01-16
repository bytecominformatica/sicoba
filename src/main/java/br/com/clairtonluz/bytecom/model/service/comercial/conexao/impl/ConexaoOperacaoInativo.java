package br.com.clairtonluz.bytecom.model.service.comercial.conexao.impl;

import br.com.clairtonluz.bytecom.model.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.IConexaoOperacao;
import br.com.clairtonluz.bytecom.model.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.model.service.provedor.IFirewall;
import br.com.clairtonluz.bytecom.model.service.provedor.impl.MikrotikFirewall;

import javax.inject.Inject;

/**
 * Created by clairtonluz on 07/12/15.
 */
public class ConexaoOperacaoInativo implements IConexaoOperacao {

    private static final IFirewall FIREWALL = new MikrotikFirewall();
    @Inject
    private IConnectionControl server;

    @Override
    public void executar(Conexao conexao, Plano plano) throws Exception {
        Secret secret = conexao.createSecret(plano);
        server.save(conexao.getMikrotik(), secret);
        FIREWALL.lock(conexao.getMikrotik(), secret);
    }
}
