package br.com.clairtonluz.sicoba.service.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.*;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.sicoba.service.provedor.IConnectionControl;
import br.com.clairtonluz.sicoba.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IConnectionControl connectionControl;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConexaoRepository conexaoRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ConexaoService conexaoService;
    @Autowired
    private BairroService bairroService;


    public List<Cliente> buscarUltimosAlterados() {
        Date data = DateUtil.toDate(LocalDateTime.now().minusMonths(2));
        return clienteRepository.findByUpdatedAtGreaterThan(data);
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findOne(id);
    }

    public boolean rgAvaliable(Cliente c) {
        Cliente cliente = null;
        if (c.getRg() != null) {
            cliente = clienteRepository.findOptionalByRg(c.getRg());
        }
        return cliente == null || cliente.getId() == c.getId();
    }

    public boolean cpfCnpjAvaliable(Cliente c) {
        Cliente cliente = clienteRepository.findOptionalByCpfCnpj(c.getCpfCnpj());
        return cliente == null || cliente.getId() == c.getId();
    }

    public boolean emailAvaliable(Cliente c) {
        Cliente cliente = null;
        if (c.getEmail() != null) {
            cliente = clienteRepository.findOptionalByEmail(c.getEmail());
        }
        return cliente == null || cliente.getId() == c.getId();
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

    public boolean isAvaliable(Cliente cliente) {
        System.out.println("verificar como vai ficar isso aqui");
//        TODO: verificar como vai ficar isso aqui
        if (!rgAvaliable(cliente)) {
            throw new RuntimeException("RG já Cadastrado");
        } else if (!cpfCnpjAvaliable(cliente)) {
            throw new RuntimeException("CPF já Cadastrado");
        } else if (!emailAvaliable(cliente)) {
            throw new RuntimeException("E-Mail já Cadastrado");
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
    }

    public List<Cliente> query(String nome, StatusCliente status) {
        List<Cliente> result;
        if (nome != null && !nome.isEmpty()) {
            result = clienteRepository.findByNomeLike("%" + nome.toUpperCase() + "%");
        } else if (status != null) {
            result = clienteRepository.findByStatus(status);
        } else {
            result = (List<Cliente>) clienteRepository.findAll();
        }
        return result;
    }

    public Conexao buscarPorCliente(Integer clienteId) {
        return conexaoRepository.findOptionalByCliente(buscarPorId(clienteId));
    }
}
