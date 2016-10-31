package br.com.clairtonluz.sicoba.service.comercial.conexao;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.sicoba.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.service.provedor.Servidor;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by clairtonluz on 07/12/15.
 */
@Service
public class ConexaoService {

    @Autowired
    private ConexaoRepository conexaoRepository;
    @Autowired
    private Servidor servidor;
    @Autowired
    private SecretService secretService;
    @Autowired
    private ContratoRepository contratoRepository;

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

        Collection<List<Conexao>> conexoesPorMk = separarPorMikrotik(list);

        for (List<Conexao> conexoes : conexoesPorMk) {
            Mikrotik mikrotik = conexoes.get(0).getMikrotik();
            try (ApiConnection con = servidor.connect(mikrotik.getHost(), mikrotik.getPort(), mikrotik.getLogin(), mikrotik.getPass())) {
                for (Conexao c : conexoes) {
                    Secret secret = c.createSecret(planos.get(c.getId()));
                    c.getCliente().getStatus().atualizarSecret(secretService, servidor, secret);
                }
            }
        }
    }

    private Collection<List<Conexao>> separarPorMikrotik(List<Conexao> list) {
        Map<Integer, List<Conexao>> map = new HashMap<>();

        for (Conexao c : list) {
            Integer id = c.getMikrotik().getId();

            if (map.get(id) == null) {
                map.put(id, new ArrayList<>());
            }
            map.get(id).add(c);
        }
        return map.values();
    }


    @Transactional
    private Map<Integer, Plano> getPlanos(List<Conexao> list) {
        Map<Integer, Plano> planos = new HashMap<>();
        for (Conexao c : list) {

            if (c.getIp() == null || c.getIp().isEmpty()) {
                c.setIp(buscarIpLivre());
            }

            Plano plano = contratoRepository.findOptionalByCliente_id(c.getCliente().getId()).getPlano();
            planos.put(c.getId(), plano);
            if (c.getCliente().getStatus() == StatusCliente.CANCELADO) {
                conexaoRepository.delete(c);
            }
        }
        return planos;
    }

    @Transactional
    public Conexao save(Conexao conexao) {
        if (isDisponivel(conexao)) {
            Plano plano = contratoRepository.findOptionalByCliente_id(conexao.getCliente().getId()).getPlano();
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

    private void atualizarNoServidor(Conexao conexao, Plano plano) {
        Secret secret = conexao.createSecret(plano);

        Mikrotik mikrotik = conexao.getMikrotik();
        try (ApiConnection con = servidor.connect(mikrotik.getHost(), mikrotik.getPort(), mikrotik.getLogin(), mikrotik.getPass())) {
            conexao.getCliente().getStatus().atualizarSecret(secretService, servidor, secret);
        } catch (ApiConnectionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean isDisponivel(Conexao conexao) {
        Conexao conexao2 = conexaoRepository.findOptionalByNome(conexao.getNome());
        return conexao2 == null || conexao2.getId().equals(conexao.getId());
    }

    @Transactional
    public void remove(Integer id) throws Exception {
        Conexao c = conexaoRepository.findOne(id);
        Plano plano = contratoRepository.findOptionalByCliente_id(c.getCliente().getId()).getPlano();

        Secret secret = c.createSecret(plano);
        Mikrotik mikrotik = c.getMikrotik();

        try (ApiConnection con = servidor.connect(mikrotik.getHost(), mikrotik.getPort(), mikrotik.getLogin(), mikrotik.getPass())) {
            secretService.remove(servidor, secret);
        }

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
