package br.com.clairtonluz.bytecom.model.service.comercial.conexao;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.impl.ConexaoOperacaoAtivo;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.impl.ConexaoOperacaoCancelado;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.impl.ConexaoOperacaoInativo;

import javax.inject.Inject;

/**
 * Created by clairtonluz on 07/12/15.
 */
public class ConexaoOperacaoFactory {
    @Inject
    private ConexaoOperacaoInativo conexaoOperacaoInativo;
    @Inject
    private ConexaoOperacaoAtivo conexaoOperacaoAtivo;
    @Inject
    private ConexaoOperacaoCancelado conexaoOperacaoCancelado;

    public IConexaoOperacao create(Conexao conexao) {
        IConexaoOperacao operacao = null;
        StatusCliente statusCliente = conexao.getCliente().getStatus();
        switch (statusCliente) {
            case INATIVO:
                operacao = conexaoOperacaoInativo;
                break;
            case ATIVO:
                operacao = conexaoOperacaoAtivo;
                break;
            case CANCELADO:
                operacao = conexaoOperacaoCancelado;
                break;
        }
        return operacao;
    }
}
