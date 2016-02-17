package br.com.clairtonluz.sicoba.service.comercial.conexao;

import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.service.comercial.conexao.impl.ConexaoOperacaoAtivo;
import br.com.clairtonluz.sicoba.service.comercial.conexao.impl.ConexaoOperacaoCancelado;
import br.com.clairtonluz.sicoba.service.comercial.conexao.impl.ConexaoOperacaoInativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by clairtonluz on 07/12/15.
 */
@Service
public class ConexaoOperacaoFactory {
    @Autowired
    private ConexaoOperacaoInativo conexaoOperacaoInativo;
    @Autowired
    private ConexaoOperacaoAtivo conexaoOperacaoAtivo;
    @Autowired
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
