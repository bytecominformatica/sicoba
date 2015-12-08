package br.com.clairtonluz.bytecom.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ClienteJPA;
import br.com.clairtonluz.bytecom.model.jpa.comercial.ConexaoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.service.provedor.MikrotikConnection;
import br.com.clairtonluz.bytecom.util.MensagemException;

import javax.inject.Inject;
import java.io.Serializable;
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
    private ConnectionService connectionService;

    public List<Cliente> buscaUltimosClientesAlterados() {
        return clienteJPA.buscaUltimosClientesAlterados();
    }

    public Cliente buscarPorId(int id) {
        return clienteJPA.buscarPorId(id);
    }

    public List<Cliente> buscarTodosPorNomeIp(String nome, String ip, String mac, StatusCliente status) {
        return clienteJPA.buscarTodosClientePorNomeIp(nome, ip, mac, status);
    }

    public boolean rgAvaliable(Cliente c) {
        Cliente cliente = clienteJPA.findByRg(c.getRg());
        return cliente == null || cliente.getId() == c.getId();
    }

    public boolean cpfCnpjAvaliable(Cliente c) {
        Cliente cliente = clienteJPA.findByCpfCnpj(c.getCpfCnpj());
        return cliente == null || cliente.getId() == c.getId();
    }

    public boolean emailAvaliable(Cliente c) {
        Cliente cliente = clienteJPA.findByEmail(c.getEmail());
        return cliente == null || cliente.getId() == c.getId();
    }

    public void remove(Cliente cliente) {
        clienteJPA.remove(cliente);
    }

    public void save(Cliente cliente) throws Exception {
        if (isAvaliable(cliente)) {
            cliente.getStatus().atualizarConexao(cliente, connectionControl);
            clienteJPA.save(cliente);
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
        List<Conexao> list = connectionService.buscarTodos();

        try (MikrotikConnection mks = connectionControl.setAutoCloseable(true)) {
            for (Conexao c : list) {
                if (c.getIp() == null || c.getIp().isEmpty()) {
                    c.setIp(conexaoJPA.getIpLivre());
                }

                c.getCliente().getStatus().atualizarConexao(c.getCliente(), connectionControl);
                connectionService.save(c);
            }
        }
        connectionControl.setAutoCloseable(false);
    }

    public List<Cliente> findAll() {
        return clienteJPA.findAll();
    }
}
