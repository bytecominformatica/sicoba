package br.com.clairtonluz.sicoba.service.comercial;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.*;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.sicoba.service.financeiro.TituloService;
import br.com.clairtonluz.sicoba.service.generic.CrudService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService extends CrudService<Cliente, ClienteRepository, Integer> {

    private ConexaoRepository conexaoRepository;
    private TituloService tituloService;
    private ContratoRepository contratoRepository;
    private ConexaoService conexaoService;
    private BairroService bairroService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ConexaoRepository conexaoRepository,
                          TituloService tituloService, ContratoRepository contratoRepository,
                          ConexaoService conexaoService, BairroService bairroService) {
        super(clienteRepository);
        this.conexaoRepository = conexaoRepository;
        this.tituloService = tituloService;
        this.contratoRepository = contratoRepository;
        this.conexaoService = conexaoService;
        this.bairroService = bairroService;
    }

    public List<Cliente> buscarUltimosAlterados() {
        Date data = DateUtil.toDate(LocalDateTime.now());
        return repository.findTop20ByOrderByUpdatedAtDesc();
    }

    public List<Cliente> findAll(Map<String, Object> params) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("nome", match -> match.ignoreCase().contains());
        Example<Cliente> example = toQuery(params, matcher);
        return super.findAll(example);
    }

    public boolean rgAvaliable(Cliente c) {
        Cliente cliente = null;
        if (c.getRg() != null) {
            cliente = repository.findOptionalByRg(c.getRg());
        }
        return cliente == null || cliente.getId().equals(c.getId());
    }

    public boolean cpfCnpjAvaliable(Cliente c) {
        Cliente cliente = repository.findOptionalByCpfCnpj(c.getCpfCnpj());
        return cliente == null || cliente.getId().equals(c.getId());
    }

    public boolean emailAvaliable(Cliente c) {
        Cliente cliente = null;
        if (c.getEmail() != null) {
            cliente = repository.findOptionalByEmail(c.getEmail());
        }
        return cliente == null || cliente.getId().equals(c.getId());
    }

    @Transactional
    public Cliente save(Cliente cliente) {
        if (cliente.getEndereco().getBairro().getId() == null) {
            Bairro bairro = bairroService.buscarOuCriarBairro(cliente.getEndereco());
            cliente.getEndereco().setBairro(bairro);
        }

        if (isAvaliable(cliente)) {
            repository.save(cliente);
            Conexao conexao = conexaoService.buscarOptionalPorCliente(cliente);

            if (conexao != null) {
                conexaoService.save(conexao);
            }

            if (cliente.getStatus().equals(StatusCliente.CANCELADO)) {
                efetuarCancelamento(cliente);
            }
        }

        return cliente;
    }

    private void efetuarCancelamento(Cliente cliente) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(cliente.getId());
        if (contrato != null) {
            contrato.setEquipamento(null);
            contrato.setEquipamentoWifi(null);
            contratoRepository.save(contrato);
        }
        List<Titulo> titulosNaoVencidos = tituloService.buscarNaoVencidosPorCliente(cliente);

        titulosNaoVencidos.forEach(it -> {
            tituloService.remove(it.getId());
        });
    }

    public boolean isAvaliable(Cliente cliente) {
        if (!rgAvaliable(cliente)) {
            throw new ConflitException("RG já Cadastrado");
        } else if (!cpfCnpjAvaliable(cliente)) {
            throw new ConflitException("CPF já Cadastrado");
        } else if (!emailAvaliable(cliente)) {
            throw new ConflitException("E-Mail já Cadastrado");
        }
        return true;
    }

    public List<Cliente> buscarSemTitulo() {
        Date data = DateUtil.toDate(LocalDate.now());
        List<Cliente> clientes = repository.findBySemTitulosDepoisDe(data);
        return clientes;
    }

    public void ativar(Cliente cliente) {
        cliente.setStatus(StatusCliente.ATIVO);
        repository.save(cliente);
        Conexao conexao = conexaoService.buscarOptionalPorCliente(cliente);
        conexaoService.save(conexao);
    }

    public void inativar(Cliente cliente) {
        cliente.setStatus(StatusCliente.INATIVO);
        repository.save(cliente);
        Conexao conexao = conexaoService.buscarOptionalPorCliente(cliente);
        conexaoService.save(conexao);
    }

    public List<Cliente> query(String nome, StatusCliente status) {
        List<Cliente> result;
        if (nome != null && !nome.isEmpty()) {
            result = repository.findByNomeLike("%" + nome.toUpperCase() + "%");
        } else if (status != null) {
            result = repository.findByStatus(status);
        } else {
            result = (List<Cliente>) repository.findAll();
        }
        return result;
    }

    public Conexao buscarConexaoPorCliente(Integer clienteId) {
        return conexaoRepository.findOptionalByCliente(findById(clienteId));
    }

    public List<Cliente> buscarUltimosCancelados() {
        Date data = DateUtil.toDate(LocalDate.now().minusMonths(2));
        return repository.findByStatusAndUpdatedAtGreaterThanOrderByUpdatedAtDesc(StatusCliente.CANCELADO, data);
    }

    @Override
    public Class<Cliente> getEntityClass() {
        return Cliente.class;
    }
}
