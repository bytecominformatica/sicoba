package br.com.clairtonluz.bytecom.model.service.comercial.conexao;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by clairtonluz on 07/12/15.
 */
public class ConexaoService implements Serializable {

    @Inject
    private ConexaoRepository conexaoRepository;
    @Inject
    private ContratoService contratoService;
    @Inject
    private ConexaoOperacaoFactory conexaoOperacaoFactory;

    public List<Conexao> buscarTodos() {
        return conexaoRepository.findAllOrderByNomeAsc();
    }

    public Conexao buscarPorCliente(Cliente cliente) {
        return conexaoRepository.findOptionalByCliente(cliente);
    }

    public void save(Conexao conexao) throws Exception {
        Plano plano = contratoService.buscarPorCliente(conexao.getCliente()).getPlano();
        conexaoOperacaoFactory.create(conexao).executar(conexao, plano);
        conexaoRepository.save(conexao);
    }

    public boolean isDisponivel(Conexao conexao) {
        Conexao conexao2 = conexaoRepository.findOptionalByNome(conexao.getNome());
        return conexao2 == null || conexao2.getId() == conexao.getId();
    }

    public void remove(Conexao conexao) {
        conexaoRepository.remove(conexao);
    }

    public String buscarIpLivre() {
        String rede = "10.77.3.";
        String ipLivre = null;
        for (int i = 10; i <= 250; i++) {
            Conexao result = conexaoRepository.findOptionalByIp(rede + i);
            if (result == null) {
                ipLivre = rede + i;
                break;
            }
        }
        return ipLivre;
    }

    public Conexao buscarPorIp(String ip) {
        return conexaoRepository.findOptionalByIp(ip);
    }
}
