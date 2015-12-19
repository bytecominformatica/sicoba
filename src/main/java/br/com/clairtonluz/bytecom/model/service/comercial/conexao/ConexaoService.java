package br.com.clairtonluz.bytecom.model.service.comercial.conexao;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ConexaoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by clairtonluz on 07/12/15.
 */
public class ConexaoService implements Serializable {

    @Inject
    private ConexaoJPA conexaoJPA;
    @Inject
    private ContratoService contratoService;
    @Inject
    private ConexaoOperacaoFactory conexaoOperacaoFactory;

    public List<Conexao> buscarTodos() {
        return conexaoJPA.buscarTodos();
    }

    public Conexao buscarPorCliente(Cliente cliente) {
        return conexaoJPA.buscarPorCliente(cliente);
    }

    public void save(Conexao conexao) throws Exception {
        Plano plano = contratoService.buscarPorCliente(conexao.getCliente()).getPlano();
        conexaoOperacaoFactory.create(conexao).executar(conexao, plano);
        conexaoJPA.save(conexao);
    }

    public boolean isDisponivel(Conexao conexao) {
        return conexaoJPA.isDisponivel(conexao);
    }

    public void remove(Conexao conexao) {
        conexaoJPA.remove(conexao);
    }

    public String buscarIpLivre() {
        return conexaoJPA.buscarIpLivre();
    }

}
