package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ClienteJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.*;
import br.com.clairtonluz.bytecom.model.repository.comercial.ClienteRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.bytecom.model.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.util.DateUtil;
import br.com.clairtonluz.bytecom.util.MensagemException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ClienteService implements Serializable {

    private static final long serialVersionUID = -8296012997453708684L;

    @Inject
    private IConnectionControl connectionControl;
    @Inject
    private ClienteJPA clienteJPA;
    @Inject
    private ClienteRepository clienteRepository;
    @Inject
    private ConexaoRepository conexaoRepository;
    @Inject
    private ContratoRepository contratoRepository;
    @Inject
    private ConexaoService conexaoService;
    @Inject
    private BairroService bairroService;


    public List<Cliente> buscarUltimosAlterados() {
        Date data = DateUtil.toDate(LocalDateTime.now().minusMonths(2));
        return clienteRepository.findByUpdatedAtGreaterThan(data);
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findBy(id);
    }

    public List<Cliente> buscarTodosPorNomeIp(String nome, String ip, StatusCliente status) {
        return clienteJPA.buscarTodosClientePorNomeIp(nome, ip, status);
    }

    public boolean rgAvaliable(Cliente c) {
        Cliente cliente = clienteRepository.findOptionalByRg(c.getRg());
        return cliente == null || cliente.getId() == c.getId();
    }

    public boolean cpfCnpjAvaliable(Cliente c) {
        Cliente cliente = clienteRepository.findOptionalByCpfCnpj(c.getCpfCnpj());
        return cliente == null || cliente.getId() == c.getId();
    }

    public boolean emailAvaliable(Cliente c) {
        Cliente cliente = clienteRepository.findOptionalByEmail(c.getEmail());
        return cliente == null || cliente.getId() == c.getId();
    }

    public void remove(Cliente cliente) {
        clienteJPA.remove(cliente);
    }

    @Transactional
    public Cliente save(Cliente cliente) throws Exception {
        if (cliente.getEndereco().getBairro().getId() == null) {
            Bairro bairro = bairroService.buscarOuCriarBairro(cliente.getEndereco());
            cliente.getEndereco().setBairro(bairro);
        }

        if (isAvaliable(cliente)) {
            clienteRepository.save(cliente);
            Contrato contrato = contratoRepository.findOptionalByCliente_id(cliente.getId());
            Conexao conexao = conexaoService.buscarOptionalPorCliente(cliente);

            if (conexao != null) {
                conexaoService.save(conexao);
            }

            if (cliente.getStatus().equals(StatusCliente.CANCELADO) && contrato != null) {
                contrato.setEquipamento(null);
                contrato.setEquipamentoWifi(null);
                contratoRepository.save(contrato);
            }
        }

        return cliente;
    }

    public boolean isAvaliable(Cliente cliente) throws MensagemException {
        if (!rgAvaliable(cliente)) {
            throw new MensagemException("RG já Cadastrado");
        } else if (!cpfCnpjAvaliable(cliente)) {
            throw new MensagemException("CPF já Cadastrado");
        } else if (!emailAvaliable(cliente)) {
            throw new MensagemException("E-Mail já Cadastrado");
        }
        return true;
    }

    public void atualizarTodasConexoes() throws Exception {
        List<Conexao> list = conexaoService.buscarTodos();

        for (Conexao c : list) {
            Cliente cliente = c.getCliente();
            if (c.getIp() == null || c.getIp().isEmpty()) {
                c.setIp(conexaoService.buscarIpLivre());
            }
            conexaoService.save(c);
        }
    }

    public List<Cliente> buscarSemTitulo() {
        Date data = DateUtil.toDate(LocalDate.now().plusMonths(2));
        return clienteRepository.findBySemTitulosDepoisDe(data);
//        return clienteJPA.buscarSemTitulo();
    }

    public List<Cliente> query(String nome, StatusCliente status) {
        List<Cliente> result;
        if (nome != null && !nome.isEmpty()) {
            result = clienteRepository.findByNomeLike("%" + nome + "%");
        } else if (status != null) {
            result = clienteRepository.findByStatus(status);
        } else {
            result = clienteRepository.findAll();
        }
        return result;
    }

    public Conexao buscarPorCliente(Integer clienteId) {
        return conexaoRepository.findOptionalByCliente(buscarPorId(clienteId));
    }
}
