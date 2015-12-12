package br.com.clairtonluz.bytecom.model.service.comercial.conexao;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.impl.ConexaoOperacaoAtivo;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.impl.ConexaoOperacaoCancelado;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.impl.ConexaoOperacaoInativo;

/**
 * Created by clairtonluz on 07/12/15.
 */
public class ConexaoOperacaoFactory {

    public IConexaoOperacao create(Conexao conexao) {
        IConexaoOperacao operacao = null;
        StatusCliente statusCliente = conexao.getCliente().getStatus();
        switch (statusCliente) {
            case INATIVO:
                operacao = new ConexaoOperacaoInativo();
                break;
            case ATIVO:
                operacao = new ConexaoOperacaoAtivo();
                break;
            case CANCELADO:
                operacao = new ConexaoOperacaoCancelado();
                break;
        }
        return operacao;
    }
}
