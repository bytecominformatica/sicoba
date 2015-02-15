package net.servehttp.bytecom.dashboard.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.dashboard.jpa.DashboadJPA;
import net.servehttp.bytecom.financeiro.jpa.entity.Mensalidade;

import com.servehttp.bytecom.commons.StringUtil;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class DashboardController implements Serializable {

  private static final long serialVersionUID = 8827281306259995250L;

  @Inject
  private DashboadJPA dashboadJPA;
  private long quantidadeInstalacoes;
  private double faturamentoDoMes;
  private double faturamentoPrevistoDoMes;
  private List<Mensalidade> listMensalidadesAtrasadas;
  private List<Cliente> listClientesInstalados;
  private List<Cliente> listClientesSemMensalidades;
  private List<Cliente> listClientesInativos;

  @PostConstruct
  public void load() {
    listClientesInstalados = dashboadJPA.buscarTodosClienteInstaladosRecente();
    quantidadeInstalacoes = dashboadJPA.getQuantidadeInstalacoesDoMes();
    faturamentoDoMes = dashboadJPA.getFaturamentoDoMes();
    faturamentoPrevistoDoMes = dashboadJPA.getFaturamentoPrevistoDoMes();
    listMensalidadesAtrasadas = dashboadJPA.getMensalidadesEmAtraso();
    listClientesInativos = dashboadJPA.getClientesInativos();
    listClientesSemMensalidades = dashboadJPA.getClientesSemMensalidade();
  }

  public List<Cliente> getListClientesInstalados() {
    return listClientesInstalados;
  }

  public long getQuantidadeInstalacoes() {
    return quantidadeInstalacoes;
  }

  public String getFaturamentoDoMes() {
    return StringUtil.formatCurrence(faturamentoDoMes);
  }

  public String getFaturamentoPrevistoDoMes() {
    return StringUtil.formatCurrence(faturamentoPrevistoDoMes);
  }

  public List<Mensalidade> getListMensalidadesAtrasadas() {
    return listMensalidadesAtrasadas;
  }

  public List<Cliente> getListClientesSemMensalidades() {
    return listClientesSemMensalidades;
  }

  public List<Cliente> getListClientesInativos() {
    return listClientesInativos;
  }

}
