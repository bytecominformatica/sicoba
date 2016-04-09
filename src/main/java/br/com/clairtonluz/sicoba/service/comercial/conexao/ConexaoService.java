package br.com.clairtonluz.sicoba.service.comercial.conexao;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.sicoba.service.comercial.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by clairtonluz on 07/12/15.
 */
@Service
public class ConexaoService {

    @Autowired
    private ConexaoRepository conexaoRepository;
    @Autowired
    private ContratoService contratoService;
    @Autowired
    private ConexaoOperacaoFactory conexaoOperacaoFactory;

    public List<Conexao> buscarTodos() {
        return conexaoRepository.findAllByOrderByNomeAsc();
    }

    @Transactional
    public Conexao buscarOptionalPorCliente(Cliente cliente) {
        return conexaoRepository.findOptionalByCliente(cliente);
    }

    @Async
    public void atualizarTodos() throws Exception {
        List<Conexao> list = buscarTodos();

        Map<Integer, Plano> planos = getPlanos(list);

        for (Conexao c : list) {
            atualizarNoServidor(c, planos.get(c.getId()));
        }
    }

    @Transactional
    private Map<Integer, Plano> getPlanos(List<Conexao> list) {
        Map<Integer, Plano> planos = new HashMap<>();
        for (Conexao c : list) {

            if (c.getIp() == null || c.getIp().isEmpty()) {
                c.setIp(buscarIpLivre());
            }

            Plano plano = contratoService.buscarPorCliente(c.getCliente().getId()).getPlano();
            planos.put(c.getId(), plano);
            if (c.getCliente().getStatus() == StatusCliente.CANCELADO) {
                conexaoRepository.delete(c);
            }
        }
        return planos;
    }

    @Transactional
    public Conexao save(Conexao conexao) throws Exception {
        if (isDisponivel(conexao)) {
            Plano plano = contratoService.buscarPorCliente(conexao.getCliente().getId()).getPlano();
            atualizarNoServidor(conexao, plano);

            if (conexao.getCliente().getStatus() == StatusCliente.CANCELADO) {
                conexaoRepository.delete(conexao);
            } else {
                conexaoRepository.save(conexao);
            }
            return conexao;
        } else {
            throw new ConflitException(conexao.getNome() + " já esta sendo utilizado");
        }
    }

    private void atualizarNoServidor(Conexao conexao, Plano plano) throws Exception {
        conexaoOperacaoFactory.create(conexao).executar(conexao, plano);
    }

    public boolean isDisponivel(Conexao conexao) {
        Conexao conexao2 = conexaoRepository.findOptionalByNome(conexao.getNome());
        return conexao2 == null || conexao2.getId().equals(conexao.getId());
    }

    @Transactional
    public void remove(Integer id) throws Exception {
        Conexao c = conexaoRepository.findOne(id);
        Plano plano = contratoService.buscarPorCliente(c.getCliente().getId()).getPlano();
        conexaoOperacaoFactory.create(c).remover(c,plano);
        conexaoRepository.delete(c);

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
