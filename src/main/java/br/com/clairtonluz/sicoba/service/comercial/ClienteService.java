package br.com.clairtonluz.sicoba.service.comercial;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.*;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.ChargeRepository;
import br.com.clairtonluz.sicoba.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.sicoba.service.financeiro.TituloService;
import br.com.clairtonluz.sicoba.service.generic.CrudService;
import br.com.clairtonluz.sicoba.service.notification.EmailService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService extends CrudService<Cliente, ClienteRepository, Integer> {
    public static final int DELAY_TOLERANCE_IN_DAYS = 15;

    private final ConexaoRepository conexaoRepository;
    private final ChargeRepository chargeRepository;
    private final TituloService tituloService;
    private final ContratoRepository contratoRepository;
    private final ConexaoService conexaoService;
    private final BairroService bairroService;
    private final EmailService emailService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ConexaoRepository conexaoRepository,
                          ChargeRepository chargeRepository, TituloService tituloService, ContratoRepository contratoRepository,
                          ConexaoService conexaoService, BairroService bairroService,
                          EmailService emailService) {
        super(clienteRepository);
        this.conexaoRepository = conexaoRepository;
        this.chargeRepository = chargeRepository;
        this.tituloService = tituloService;
        this.contratoRepository = contratoRepository;
        this.conexaoService = conexaoService;
        this.bairroService = bairroService;
        this.emailService = emailService;
    }

    public List<Cliente> buscarUltimosAlterados() {
        return repository.findTop20ByOrderByUpdatedAtDesc();
    }

    public List<Cliente> findAll(Map<String, Object> params) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("nome", match -> match.ignoreCase().contains());
        Example<Cliente> example = toQuery(params, matcher);
        return super.findAll(example);
    }

    public Map<String, String> blockLateCustomers() {
        try {
            Map<String, String> response = new HashMap<>();

            LocalDate now = LocalDate.now();
            List<Charge> lateCharges = chargeRepository.overdue(now.minusDays(DELAY_TOLERANCE_IN_DAYS));
            StringBuilder blockedCustomers = new StringBuilder();
            StringBuilder bypassCustomers = new StringBuilder();
            lateCharges.forEach(charge -> {
                Cliente cliente = charge.getCliente();
                if (StatusCliente.ATIVO.equals(cliente.getStatus())) {
                    LocalDate bypass = cliente.getBypassAutoBlockUntil();
                    if (bypass == null || now.isAfter(bypass)) {
                        inativar(cliente);
                        addCustomersToStringBuilder(blockedCustomers, cliente);
                    } else {
                        addCustomersToStringBuilder(bypassCustomers, cliente);
                    }
                }
            });

            if (blockedCustomers.length() > 0) {
                String subject = "[NOTIFICATION] Bloqueio automatico de clientes em atraso";
                emailService.sendToSac(subject,
                        "Clientes bloqueados:\n" +
                                blockedCustomers.toString() +
                                "\nClientes não bloqueados por decisões administrativas:\n" +
                                bypassCustomers.toString());
            }

            response.put("blocked", blockedCustomers.toString());
            response.put("bypassed", bypassCustomers.toString());
            return response;
        } catch (Exception e) {
            emailService.notificarAdmin("[NOTIFICATION] Erro ao bloquear automaticamente clientes atrasados", e);
            return null;
        }
    }

    private void addCustomersToStringBuilder(StringBuilder sb, Cliente cliente) {
        if (sb.length() > 0) {
            sb.append(", \n");
        }
        sb.append(cliente.getId());
        sb.append(" - ");
        sb.append(cliente.getNome());
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
        return repository.findBySemTitulosDepoisDe(LocalDate.now());
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
        if (conexao != null)
            conexaoService.save(conexao);
    }

    public List<Cliente> query(String nome, StatusCliente status) {
        List<Cliente> result;
        if (nome != null && !nome.isEmpty()) {
            result = repository.findByNomeLike("%" + nome.toUpperCase() + "%");
        } else if (status != null) {
            result = repository.findByStatus(status);
        } else {
            result = repository.findAll();
        }
        return result;
    }

    public Conexao buscarConexaoPorCliente(Integer clienteId) {
        return conexaoRepository.findOptionalByCliente(findById(clienteId));
    }

    public List<Cliente> buscarUltimosCancelados() {
        return repository.findByStatusAndUpdatedAtGreaterThanOrderByUpdatedAtDesc(StatusCliente.CANCELADO, LocalDateTime.now().minusMonths(2));
    }

    @Override
    public Class<Cliente> getEntityClass() {
        return Cliente.class;
    }
}
