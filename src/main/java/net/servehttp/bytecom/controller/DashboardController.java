package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.DashboadJPA;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Acesso;
import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;
import net.servehttp.bytecom.util.StringUtil;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class DashboardController implements Serializable {

  private static final long serialVersionUID = 8827281306259995250L;

  @Inject
  private GenericoJPA genericoJPA;
  @Inject
  private DashboadJPA dashboadJPA;
  private long quantidadeInstalacoes;
  private double faturamentoDoMes;
  private double faturamentoPrevistoDoMes;
  private List<Mensalidade> listMensalidadesAtrasadas;
  private List<Cliente> listClientesInstalados;
  private List<Cliente> listClientesSemMensalidades;
  private List<Cliente> listClientesInativos;
  private List<Acesso> quantidadeClientesInativos;

  @PostConstruct
  public void load() {
    listClientesInstalados = genericoJPA.buscarTodos(Cliente.class, false, "createdAt", 10);
    quantidadeInstalacoes = dashboadJPA.getQuantidadeInstalacoesDoMes();
    faturamentoDoMes = dashboadJPA.getFaturamentoDoMes();
    faturamentoPrevistoDoMes = dashboadJPA.getFaturamentoPrevistoDoMes();
    listMensalidadesAtrasadas = dashboadJPA.getMensalidadesEmAtraso();
    quantidadeClientesInativos = dashboadJPA.getQuantidadeClientesInativos();
    listClientesInativos = dashboadJPA.getClientesInativos();
//    listClientesSemMensalidades = dashboadJPA.getClientesSemMensalidade();
  }

  public List<Cliente> getListClientesInstalados() {
    return listClientesInstalados;
  }

  public long getQuantidadeInstalacoes() {
    return quantidadeInstalacoes;
  }

  public String getFaturamentoDoMes() {
    return StringUtil.INSTANCE.formatCurrence(faturamentoDoMes);
  }

  public String getFaturamentoPrevistoDoMes() {
    return StringUtil.INSTANCE.formatCurrence(faturamentoPrevistoDoMes);
  }

  public List<Mensalidade> getListMensalidadesAtrasadas() {
    return listMensalidadesAtrasadas;
  }

  public List<Acesso> getQuantidadeClientesInativos() {
    return quantidadeClientesInativos;
  }

  public List<Cliente> getListClientesSemMensalidades() {
    return listClientesSemMensalidades;
  }

  public List<Cliente> getListClientesInativos() {
    return listClientesInativos;
  }

}
