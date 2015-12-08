package br.com.clairtonluz.bytecom.controller.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.IConnectionClienteCertified;
import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Secret;
import br.com.clairtonluz.bytecom.model.service.comercial.ClienteService;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;
import br.com.clairtonluz.bytecom.model.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.model.service.provedor.MikrotikService;
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

    private Conexao conexao;
    @Inject
    private ConexaoService conexaoService;
    @Inject
    private MikrotikService mikrotikService;
    @Inject
    private IConnectionControl connectionControl;
    @Inject
    private ClienteService clienteService;
    @Inject
    private ContratoService contratoService;
    private Mikrotik mikrotik;
    private Cliente cliente;


    private List<Mikrotik> listMikrotik;

    @PostConstruct
    public void load() {
        carregarCliente();
        buscarTodosMikrotik();
    }

    public void buscarTodosMikrotik() {
        listMikrotik = mikrotikService.findAll();
    }

    private void carregarCliente() {
        String clienteId = WebUtil.getParameters("id");
        if (clienteId != null && !clienteId.isEmpty()) {
            cliente = clienteService.buscarPorId(Integer.valueOf(clienteId));
            conexao = conexaoService.buscarPorCliente(cliente);
        }

        if (cliente == null) {
            AlertaUtil.error("Nenhum cliente selecionado");
        } else if (conexao == null) {
            novaConexao();
        }
    }

    public void salvar() throws Exception {
        if (valido()) {
            conexaoService.save(conexao);
            AlertaUtil.info("Salvo com sucesso!");
        }
    }

    private boolean valido() {
        boolean valido = true;
        if (conexao.getCliente().getStatus().equals(StatusCliente.CANCELADO)) {
            valido = false;
            AlertaUtil.error("Cliente cancelado nao pode possuir uma conexao");
        } else if (!conexaoService.isDisponivel(conexao)) {
            valido = false;
            AlertaUtil.error("Esse nome de usuário já está entityManager uso");
        }
        return valido;
    }

    public void remover() throws Exception {
        Contrato contrato = contratoService.buscarPorCliente(conexao.getCliente());
        IConnectionClienteCertified secret = new Secret(conexao.getNome(),
                conexao.getSenha(),
                conexao.getIp(),
                contrato.getPlano().getNome()
        );

        connectionControl.remove(conexao.getMikrotik(), secret);
        conexaoService.remove(conexao);
        novaConexao();
    }

    private void novaConexao() {
        conexao = new Conexao();
        conexao.setCliente(cliente);
        String login;

        if (conexao.getCliente().getNome().contains(" ")) {
            login = conexao.getCliente().getNome().substring(0, conexao.getCliente().getNome().indexOf(' ')) + conexao.getCliente().getId();
        } else {
            login = conexao.getCliente().getNome() + conexao.getCliente().getId();
        }

        login = StringUtil.removeCaracterEspecial(login);
        conexao.setNome(login);
        conexao.setSenha(login);

        conexao.setIp(conexaoService.buscarIpLivre());
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
