package br.com.clairtonluz.bytecom.controller.dashboard;

import br.com.clairtonluz.bytecom.model.jpa.dashboard.DashboadJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.bytecom.model.service.financeiro.MensalidadeService;
import br.com.clairtonluz.bytecom.util.StringUtil;

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
public class DashboardController implements Serializable {

    private static final long serialVersionUID = 8827281306259995250L;

    @Inject
    private DashboadJPA dashboadJPA;
    @Inject
    private ContratoService contractService;
    @Inject
    private ConexaoService conexaoService;
    @Inject
    private MensalidadeService mensalidadeService;
    private long quantidadeInstalacoes;
    private double faturamentoDoMes;
    private double faturamentoPrevistoDoMes;
    private List<Mensalidade> listMensalidadesAtrasadas;
    private List<Contrato> listNovosContratos;
    private List<Cliente> listClientesSemMensalidades;
    private List<Cliente> listClientesInativos;

    @PostConstruct
    public void load() {
        listNovosContratos = dashboadJPA.buscarNovosContratos();
        quantidadeInstalacoes = contractService.buscarTodosInstaladoEsseMes().size();
        faturamentoDoMes = dashboadJPA.getFaturamentoDoMes();
        faturamentoPrevistoDoMes = dashboadJPA.getFaturamentoPrevistoDoMes();
        listMensalidadesAtrasadas = dashboadJPA.getMensalidadesEmAtraso();
        listClientesInativos = dashboadJPA.getClientesInativos();
        listClientesSemMensalidades = dashboadJPA.getClientesSemMensalidade();
    }

    public Conexao getConexao(Cliente cliente) {
        return conexaoService.buscarPorCliente(cliente);
    }
    public List<Mensalidade> getMensalidades(Cliente cliente) {
        return mensalidadeService.buscarPorCliente(cliente);
    }

    public List<Contrato> getListNovosContratos() {
        return listNovosContratos;
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
