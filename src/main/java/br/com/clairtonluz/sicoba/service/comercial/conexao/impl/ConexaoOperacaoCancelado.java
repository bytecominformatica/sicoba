package br.com.clairtonluz.sicoba.service.comercial.conexao.impl;

import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
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
public class ConexaoOperacaoCancelado extends IConexaoOperacao {

    @Override
    protected void perform(IConnectionControl server, Conexao conexao, Secret secret) throws Exception {
        server.remove(conexao.getMikrotik(), secret);
    }
}
