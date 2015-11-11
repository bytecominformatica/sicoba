package br.com.clairtonluz.bytecom.controller.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ConexaoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.bytecom.model.jpa.provedor.MikrotikJPA;
import br.com.clairtonluz.bytecom.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.util.StringUtil;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;
import br.com.clairtonluz.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class ConexaoController implements Serializable {

    private static final long serialVersionUID = -676355663109515972L;

    private Cliente cliente;
    @Inject
    private ConexaoJPA conexaoJPA;
    @Inject
    private MikrotikJPA mikrotikJPA;
    @Inject
    private IConnectionControl connectionControl;
    @Inject
    private ClienteService clienteService;
    private Mikrotik mikrotik;


    private List<Mikrotik> listMikrotik;

    @PostConstruct
    public void load() {
        carregarCliente();
        buscarTodosMikrotik();
    }

    public void buscarTodosMikrotik() {
        listMikrotik = mikrotikJPA.buscarTodosMikrotik();
    }

    private void carregarCliente() {
        String clienteId = WebUtil.getParameters("id");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clienteService.buscarPorId(Integer.valueOf(clienteId));
        }

        if (cliente == null) {
            AlertaUtil.error("Nenhum cliente selecionado");
            cliente = new Cliente();
        } else if (cliente.getConexao() == null) {
            novaConexao();
        }
    }

    public void salvar() throws Exception {
        if (valido()) {
            conexaoJPA.save(cliente.getConexao());
            cliente.getStatus().atualizarConexao(cliente, connectionControl);
            AlertaUtil.info("Salvo com sucesso!");
        }
    }

    private boolean valido() {
        boolean valido = true;
        if (!conexaoJPA.conexaoDisponivel(cliente.getConexao())) {
            valido = false;
            AlertaUtil.error("Esse nome de usuário já está entityManager uso");
        }
        return valido;
    }

    public void remover() throws Exception {
        connectionControl.remove(cliente.getConexao().getMikrotik(), cliente.getConexao());
        cliente.setConexao(null);
        clienteService.save(cliente);
        novaConexao();
    }

    private void novaConexao() {
        Conexao conexao = new Conexao();
        conexao.setCliente(cliente);
        String login;

        if (cliente.getNome().contains(" ")) {
            login = cliente.getNome().substring(0, cliente.getNome().indexOf(' ')) + cliente.getId();
        } else {
            login = cliente.getNome() + cliente.getId();
        }

        login = StringUtil.removeCaracterEspecial(login);
        conexao.setNome(login);
        conexao.setSenha(login);

        conexao.setIp(conexaoJPA.getIpLivre());
        cliente.setConexao(conexao);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Mikrotik> getListMikrotik() {
        return listMikrotik;
    }

    public void setListMikrotik(List<Mikrotik> listMikrotik) {
        this.listMikrotik = listMikrotik;
    }

    public Mikrotik getMikrotik() {
        return mikrotik;
    }

    public void setMikrotik(Mikrotik mikrotik) {
        this.mikrotik = mikrotik;
    }

}
