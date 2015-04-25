package net.servehttp.bytecom.controller.comercial;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.controller.extra.GenericoController;
import net.servehttp.bytecom.persistence.jpa.comercial.ConexaoJPA;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.Conexao;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.Mikrotik;
import net.servehttp.bytecom.persistence.jpa.provedor.MikrotikJPA;
import net.servehttp.bytecom.service.provedor.IConnectionControl;
import net.servehttp.bytecom.util.StringUtil;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

/**
 * 
 * @author clairton
 *
 */
@Named
@ViewScoped
public class ConexaoController extends GenericoController {

  private static final long serialVersionUID = -676355663109515972L;

  private Cliente cliente;
  @Inject
  private ConexaoJPA conexaoJPA;
  @Inject
  private MikrotikJPA mikrotikJPA;
  @Inject
  private IConnectionControl connectionControl;
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
      cliente = jpa.buscarPorId(Cliente.class, Integer.valueOf(clienteId));
    }

    if (cliente == null) {
      AlertaUtil.error("Nenhum cliente selecionado");
      cliente = new Cliente();
    } else if (cliente.getConexao() == null) {
      novaConexao();
    }
  }

  public void salvar() {
    try {
      if (valido()) {
        connectionControl.save(cliente.getConexao().getMikrotik(), cliente.getConexao());
        jpa.salvar(cliente.getConexao());
        AlertaUtil.info("Salvo com sucesso!");
      }
    } catch (Exception e) {
      log(e);
    }
  }

  private boolean valido() {
    boolean valido = true;
    if (!conexaoJPA.conexaoDisponivel(cliente.getConexao())) {
      valido = false;
      AlertaUtil.error("Esse nome de usuário já está em uso");
    }
    return valido;
  }

  public void remover() {
    try {
      connectionControl.remove(cliente.getConexao().getMikrotik(), cliente.getConexao());
      cliente.setConexao(null);
      jpa.salvar(cliente);
      novaConexao();
    } catch (Exception e) {
      log(e);
    }
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
