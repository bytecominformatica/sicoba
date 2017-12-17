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
import br.com.clairtonluz.sicoba.service.generic.CrudService;
import br.com.clairtonluz.sicoba.service.provedor.Servidor;
import br.com.clairtonluz.sicoba.util.SendEmail;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by clairtonluz on 07/12/15.
 */
@Service
public class ConexaoService extends CrudService<Conexao, ConexaoRepository, Integer> {

    private Servidor servidor;
    private SecretService secretService;
    private ContratoRepository contratoRepository;

    @Autowired
    public ConexaoService(ConexaoRepository repository, Servidor servidor,
                          SecretService secretService, ContratoRepository contratoRepository) {
        super(repository);
        this.servidor = servidor;
        this.secretService = secretService;
        this.contratoRepository = contratoRepository;
    }

    @Override
    public List<Conexao> findAll(Map<String, Object> params) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("ip", match -> match.contains())
                .withMatcher("mac", match -> match.ignoreCase().contains());
        Example<Conexao> example = toQuery(params, matcher);
        return super.findAll(example);
    }

    @Transactional
    public Conexao buscarOptionalPorCliente(Cliente cliente) {
        return repository.findOptionalByCliente(cliente);
    }

    @Async
    public void atualizarTodos() {
        List<Conexao> list = repository.findAllByOrderByNomeAsc();
        Map<Integer, Plano> planos = getPlanos(list);

        Collection<List<Conexao>> conexoesPorMk = separarPorMikrotik(list);

        for (List<Conexao> conexoes : conexoesPorMk) {
            Mikrotik mikrotik = conexoes.get(0).getMikrotik();
            try (ApiConnection con = servidor.connect(mikrotik.getHost(), mikrotik.getPort(), mikrotik.getLogin(), mikrotik.getPass())) {
                for (Conexao c : conexoes) {
                    try {
                        Secret secret = c.createSecret(planos.get(c.getId()));
                        c.getCliente().getStatus().atualizarSecret(secretService, servidor, secret);
                    } catch (Exception e) {
                        SendEmail.notificarAdmin(e);
                    }
                }
            } catch (ApiConnectionException e) {
                SendEmail.notificarAdmin(e);
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
                repository.delete(c);
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
                repository.delete(conexao);
            } else {
                repository.save(conexao);
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
            throw new RuntimeException(e);
        }
    }

    public boolean isDisponivel(Conexao conexao) {
        Conexao conexao2 = repository.findOptionalByNome(conexao.getNome());
        return conexao2 == null || conexao2.getId().equals(conexao.getId());
    }

    @Transactional
    public void delete(Integer id) {
        Conexao c = repository.findOne(id);
        Plano plano = contratoRepository.findOptionalByCliente_id(c.getCliente().getId()).getPlano();

        Secret secret = c.createSecret(plano);
        Mikrotik mikrotik = c.getMikrotik();

        try (ApiConnection con = servidor.connect(mikrotik.getHost(), mikrotik.getPort(), mikrotik.getLogin(), mikrotik.getPass())) {
            secretService.remove(servidor, secret);
        } catch (ApiConnectionException e) {
            throw new RuntimeException(e);
        }

        repository.delete(c);
    }

    public String buscarIpLivre() {
        String rede = "10.77.3.";
        String ipLivre = null;
        for (int i = 10; i <= 250; i++) {
            Conexao result = repository.findOptionalByIp(rede + i);
            if (result == null) {
                ipLivre = rede + i;
                break;
            }
        }
        return ipLivre;
    }

    @Override
    public Class<Conexao> getEntityClass() {
        return Conexao.class;
    }
}
