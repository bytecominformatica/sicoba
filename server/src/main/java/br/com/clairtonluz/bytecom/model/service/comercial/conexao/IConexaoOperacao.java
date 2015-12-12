package br.com.clairtonluz.bytecom.model.service.comercial.conexao;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;

/**
 * Created by clairtonluz on 07/12/15.
 */
public interface IConexaoOperacao {
    void executar(Conexao conexao, Plano plano) throws Exception;
}
