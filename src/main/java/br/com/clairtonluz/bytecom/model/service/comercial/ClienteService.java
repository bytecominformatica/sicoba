package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ClienteJPA;
import br.com.clairtonluz.bytecom.model.jpa.comercial.ConexaoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.model.repository.comercial.ClienteRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.bytecom.model.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.util.MensagemException;

import javax.inject.Inject;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ClienteService implements Serializable {

    private static final long serialVersionUID = -8296012997453708684L;

    @Inject
    private ConexaoJPA conexaoJPA;
    @Inject
    private IConnectionControl connectionControl;
    @Inject
    private ClienteJPA clienteJPA;
    @Inject
    private ClienteRepository clienteRepository;
    @Inject
    private ContratoRepository contratoRepository;
    private ConexaoService conexaoService;


    public List<Cliente> buscaUltimosClientesAlterados() {
        LocalDateTime data = LocalDateTime.now().minusMonths(2);
        return clienteRepository.findByUpdateAtGreaterThan(data);
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findBy(id);
    }

    public List<Cliente> buscarTodosPorNomeIp(String nome, String ip, StatusCliente status) {
        return clienteJPA.buscarTodosClientePorNomeIp(nome, ip, status);
    }

    public boolean rgAvaliable(Cliente c) {
        Cliente cliente = clienteRepository.findByRg(c.getRg());
        return cliente == null || cliente.getId() == c.getId();
    }

    public boolean cpfCnpjAvaliable(Cliente c) {
        Cliente cliente = clienteRepository.findByCpfCnpj(c.getCpfCnpj());
        return cliente == null || cliente.getId() == c.getId();
    }

    public boolean emailAvaliable(Cliente c) {
        Cliente cliente = clienteRepository.findByEmail(c.getEmail());
        return cliente == null || cliente.getId() == c.getId();
    }

    public void remove(Cliente cliente) {
        clienteJPA.remove(cliente);
    }

    public void save(Cliente cliente) throws Exception {
        if (isAvaliable(cliente)) {
            clienteJPA.save(cliente);
            Contrato contrato = contratoRepository.findByCliente(cliente);
            Conexao conexao = conexaoService.buscarPorCliente(cliente);

            if (conexao != null) {
                conexaoService.save(conexao);
            }

            if (cliente.getStatus().equals(StatusCliente.CANCELADO) && contrato != null) {
                contrato.setEquipamento(null);
                contrato.setEquipamentoWifi(null);
                contratoRepository.save(contrato);
            }

        }
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
                c.setIp(conexaoJPA.buscarIpLivre());
            }

            conexaoService.save(c);
        }

    }

    public List<Cliente> buscarSemMensalidade() {
        return clienteJPA.buscarSemMensalidade();
    }

    public List<Cliente> buscarPorStatus(StatusCliente status) {
        return clienteRepository.findByStatus(status);
    }
}
